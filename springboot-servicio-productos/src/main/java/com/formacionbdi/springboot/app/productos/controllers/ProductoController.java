package com.formacionbdi.springboot.app.productos.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

//import com.formacionbdi.springboot.app.productos.models.entity.Producto;
import com.formacionbdi.springboot.app.commons.models.entity.Producto;
import com.formacionbdi.springboot.app.productos.models.service.IProductoService;

@RestController //Convierte a JSON lo que retornan los handler
public class ProductoController {
	
	@Autowired
	private Environment env;
	
	@Value("${server.port}") //Inyecta valores configurados en el properties
	private Integer port;
	
	@Autowired
	private IProductoService productoService;
	
	//Métodos handler
	@GetMapping("/listar") //Mapea endpoints a los métodos
	public List<Producto> listar(){
		return productoService.findAll().stream().map(producto -> {
			//producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			producto.setPort(port);
			return producto;
		}).collect(Collectors.toList());
	}
	
	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable Long id) throws Exception {
		Producto producto = productoService.findById(id);
		producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		
//		boolean ok = false; //Simular error de excepción para probar hystrix
//		if(ok == false) {
//			throw new Exception("No se pudo cargar el producto");
//		}
		
//		try { //Simular error de tiempo para probar hystrix
//			Thread.sleep(2000L); //Time out 2 seg... L de long.
//			//Para Ribbon y Hystrix el time out es de un segundo, 
//			//por lo que se lanzará un error
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
		return producto;
	}
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED) //Se indica el código de respuesta (201)
	public Producto crear(@RequestBody Producto producto) { //Toma los datos del Body y los mapea al objeto
		return productoService.save(producto);
	}
	
	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto editar(@RequestBody Producto producto, @PathVariable Long id) {
		Producto productoDb = productoService.findById(id);
		
		productoDb.setNombre(producto.getNombre());
		productoDb.setPrecio(producto.getPrecio());
		
		return productoService.save(productoDb);
	}
	
	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {
		productoService.deleteById(id);
	}
}
