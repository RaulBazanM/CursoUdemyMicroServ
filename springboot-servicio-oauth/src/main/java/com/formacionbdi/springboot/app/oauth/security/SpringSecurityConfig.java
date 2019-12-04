package com.formacionbdi.springboot.app.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//Configuración de Spring Security
//Para registrar la clase service en el authentication manager, para poder realizar el proceso de autenticación
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	@Autowired //Spring buscará un componente que implemente la interfaz UserDetailsService y lo inyectará
	private UserDetailsService usuarioService;

	@Autowired
	private AuthenticationEventPublisher eventPublisher;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	@Autowired //Para que se pueda inyectar AuthenticationManagerBuilder
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.usuarioService).passwordEncoder(passwordEncoder()) //Registra el servicio y encripta password
		.and().authenticationEventPublisher(eventPublisher);
	}

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	

}
