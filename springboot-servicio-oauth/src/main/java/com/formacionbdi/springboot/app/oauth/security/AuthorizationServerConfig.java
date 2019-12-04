package com.formacionbdi.springboot.app.oauth.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

//Configuración del servidor de autorización que se encarga del proceso de login
@Configuration
@EnableAuthorizationServer //Se habilita como servidor de autorización
@RefreshScope //Para poder actualizar con un Refresh
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

	@Autowired
	private Environment env;
	
	@Autowired
	private InfoAdicionalToken infoAdicionalToken;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	//Para los permisos que van a tener nuestros endpoints para generar y validar el token. Seguridad a los endpoint
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()") //Cualquier cliente puede accesar a la ruta para generar el token
		.checkTokenAccess("isAuthenticated()"); //Se encarga de validar el token
	}

	//Método para configurar los clientes (apps) que se comunicarán con los microServ
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(env.getProperty("config.security.oauth.client.id")) //Almacena y otorga identificador de la app
		.secret(passwordEncoder.encode(env.getProperty("config.security.oauth.client.secret"))) //Contraseña que se encripta
		.scopes("read", "write") //Alcance de la app
		.authorizedGrantTypes("password", "refresh_token") //Tipo de concesión de la aplicación, cómo se obtiene el token
		.accessTokenValiditySeconds(3600) //1 hora antes de que caduque
		.refreshTokenValiditySeconds(3600);
//		.and()
//		.withClient("androiddapp") //Almacena y otorga identificador de la app
//		.secret(passwordEncoder.encode("12345")) //Contraseña que se encripta
//		.scopes("read", "write") //Alcance de la app
//		.authorizedGrantTypes("password", "refresh_token") //Tipo de concesión de la aplicación, cómo se obtiene el token
//		.accessTokenValiditySeconds(3600) //1 hora antes de que caduque
//		.refreshTokenValiditySeconds(3600)
	}

	//Método para configurar AuthenticationManager, tokenStorage y AccesTokenConverter(agregar claims -> lo que se le agrega al token)
	@Override
	//El endpoint es /oauth/token
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception { //Se relaciona con el endpoint de Oauth2
		
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain(); //Cadena para unir datos del token
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken, accessTokenConverter()));
		
		//Registrar AuthManager
		endpoints.authenticationManager(authenticationManager)
		.tokenStore(tokenStore())
		.accessTokenConverter(accessTokenConverter())
		.tokenEnhancer(tokenEnhancerChain); //Cadena de token completa
	}

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey("config.security.oauth.jwt.key"); //Firma del token
		return tokenConverter;
	}
	
	
}
