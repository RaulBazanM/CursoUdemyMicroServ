package com.formacionbdi.springboot.app.item;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration //Permite crear componentes de Spring (Beans)
public class AppConfig {
	
	@Bean("clienteRest") //Ó se guarda con el mismo nombre que el método
	@LoadBalanced //Utiliza Ribbon para balanceo de carga en Rest Template
	public RestTemplate registrarRestTemplate() { //Cliente para trabajar con API Rest, accede a recursos en otros microServ
		return new RestTemplate(); //El objeto creado se guarda en el contenedor
	}

}
