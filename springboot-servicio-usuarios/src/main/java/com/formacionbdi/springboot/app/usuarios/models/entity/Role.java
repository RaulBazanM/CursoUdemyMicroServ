package com.formacionbdi.springboot.app.usuarios.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

//Esta clase ya no es necesaria puesto que ya se encuentra en dependecia commons

@Entity // Clase de persistencia, mapeada a una tabla
@Table(name = "roles")
public class Role implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, length = 30)
	private String nombre;

//	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles") //Relación bidireccional, habría que implementar getUsuario
//	private List<Usuario> usuarios;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4467531611801172710L;

}
