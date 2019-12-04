package com.formacionbdi.springboot.app.commons;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class}) //Deshabilitamos autoconfiguración de DataSource
public class SpringbootServicioCommonsApplication {

	// Quitamos el método main porque no será una aplicación, es un proyecto de librería
	// Será una dependencia
	// También quitamos el plug-in de Maven en el POM
	// Después de crear el jar con mvnw.cmd install, se puede borrar la dependencia de H2
	// y agregar la notación @EnableAutoConfiguration (No funcionó como en el curso)
}
