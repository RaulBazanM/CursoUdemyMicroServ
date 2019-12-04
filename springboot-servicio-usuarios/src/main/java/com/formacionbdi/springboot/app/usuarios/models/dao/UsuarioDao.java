package com.formacionbdi.springboot.app.usuarios.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.formacionbdi.springboot.app.usuarios.commons.models.entity.Usuario;


// Necesita dependencia Rest Repositories
@RepositoryRestResource(path="usuarios") //Endpoint donde se puede exportar todo el CRUD repository, formato HATEOAS
public interface UsuarioDao extends PagingAndSortingRepository<Usuario, Long>{ //Hereda también de CrudRepository pero añade funciones

	@RestResource(path="buscar-username") //Personalizar endpoint .../usuarios/search/buscar-username
	//Query method, ejecuta un select con where, el nombre debe respetar una estructura
	public Usuario findByUsername(@Param("username") String username); //Personalizar queryParam ...?username=valor
	
//	public Usuario findByUsernameAndEmail(String username, String email);
	
//	@Query("select u from Usuario u where u.username=?1 and u.email=?2") //A nivel de objeto, no de tabla
//	public Usuario obtenerPorUsername(String Username, String email);
	
	@Query("select u from Usuario u where u.username=?1") //A nivel de objeto, no de tabla
	public Usuario obtenerPorUsername(String username);
}
