package com.formacionbdi.springboot.app.usuarios;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import com.formacionbdi.springboot.app.usuarios.commons.models.entity.Role;
import com.formacionbdi.springboot.app.usuarios.commons.models.entity.Usuario;


@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer{

	//Esto es sólo por si queremos ver los id en el CRUD expuesto en un endpoint
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(Usuario.class, Role.class);
	}

	
}
