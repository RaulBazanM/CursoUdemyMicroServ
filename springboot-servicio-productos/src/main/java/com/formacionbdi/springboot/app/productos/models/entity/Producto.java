package com.formacionbdi.springboot.app.productos.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

// Esta clase ya no es necesaria porque ya se encuentra en la dependencia de commons
@Entity //Entidad relacional (JPA)
@Table(name = "productos") //Opcional, por default toma la tabla con nombre de la clase
public class Producto implements Serializable{ //Para poder pasar la información a bytes
	
	@Id //Llave primaria
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Autoincremental
	private Long id;
	
	//Columnas mapeadas de forma automática, el atributo debe llamarse como el campo de la tabla (columna)
	// de lo contrario debe indicarse
	private String nombre;
	private Double precio;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	@Transient //Atributo no persistente, no está mapeado a la base de datos
	private Integer port;
	
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
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1285454306356845809L;

}
