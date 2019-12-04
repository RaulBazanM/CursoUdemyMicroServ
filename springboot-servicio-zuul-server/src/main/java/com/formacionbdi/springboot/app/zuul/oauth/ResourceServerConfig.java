package com.formacionbdi.springboot.app.zuul.oauth;

import java.util.Arrays;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@RefreshScope //Para poder actualizar con un Refresh
@Configuration
@EnableResourceServer //Habilitar configuración del servidor de recurso
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
//Se encarga de proteger todos los accesos a los microServ y dar acceso a clientes según el token
	
	@Value("${config.security.oauth.jwt.key}")
	private String jwtKet;
	
	//Método para configurar el token (misma estructura que el servidor de autorización)
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore());
	}

	//Método para proteger rutas o endpoints
	//También sirve para configurar CORS (para que una aplicación en otro servidor pueda accesar a nuestros recursos)
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/api/security/oauth/**").permitAll()
		.antMatchers(HttpMethod.GET, "/api/productos/listar", "/api/items/listar", "/api/usuarios/usuarios").permitAll()
		.antMatchers(HttpMethod.GET, "/api/productos/ver/{id}", "/api/items/ver/{id}/cantidad/{cantidad}", 
				"/api/usuarios/usuarios/{id}").hasAnyRole("ADMIN", "USER") //No se debe colocar el prefijo ROLE_ porque se inserta automáticamente
		.antMatchers("/api/productos/**", "/api/items/**", "/api/usuarios/**").hasRole("ADMIN")
		.anyRequest().authenticated() //Cualquier otra ruta no configurada requerirá autenticación
		.and().cors().configurationSource(corsConfigurationSource());
		
//		.antMatchers(HttpMethod.POST, "/api/productos/crear", "/api/items/crear", "/api/usuarios/usuarios").hasRole("ADMIN")
//		.antMatchers(HttpMethod.PUT, "/api/productos/editar/{id}", "/api/items/editar/{id}", "/api/usuarios/usuarios/{id}").hasRole("ADMIN")
//		.antMatchers(HttpMethod.DELETE, "/api/productos/eliminar/{id}", "/api/items/eliminar/{id}", "/api/usuarios/usuarios/{id}").hasRole("ADMIN");
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		//corsConfig.addAllowedOrigin("*"); //Agregamos de forma automática cualquier origen
		corsConfig.setAllowedOrigins(Arrays.asList("*"));	
		corsConfig.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS")); //OPTIONS lo utiliza Oauth
		corsConfig.setAllowCredentials(true);
		corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
		
		//Pasar la configuración a nuestros endpoints
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig); //A todas las rutas o endpoints
		return source;
	}
	
	@Bean
	//Se configura este filtro para que se aplique de forma global, no solamente en SpringSecurity, sino en toda la app
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}

	@Bean //Es el mismo que en AuthorizationSeverConfig en proyecto Oauth
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean //Es el mismo que en AuthorizationSeverConfig en proyecto Oauth
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey(jwtKet); //Firma del token
		return tokenConverter;
	}
}
