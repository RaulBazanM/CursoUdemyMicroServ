package com.formacionbdi.springboot.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component //Para registrarlo como componente, bean
public class PostTiempoTranscurridoFilter extends ZuulFilter{

	// Se añaden logs para consola
	private static Logger log = LoggerFactory.getLogger(PostTiempoTranscurridoFilter.class);
	
	@Override
	public boolean shouldFilter() {
		// Método para validar si se utiliza el filtro
		// Si devuelve true ejecuta el método run
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		// Aquí es donde se resuelve la lógica del filtro
	
		// Necesitamos pasar parámetros al Request, hay que obtener el objeto http
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest(); // Con esto ya podemos pasar datos al Request
		log.info("Entrando a post filter");
		
		Long tiempoInicio = (Long) request.getAttribute("tiempoInicio"); // Se estableció en filtro pre. Es tipo Object, se hace cast a tipo Long
		Long tiempoFinal = System.currentTimeMillis();
		Long tiempoTranscurrido = tiempoFinal - tiempoInicio;
		
		log.info(String.format("Tiempo transcurrido en segundos %s seg.", tiempoTranscurrido.doubleValue()/1000.00));
		log.info(String.format("Tiempo transcurrido en milisegundos %s ms.", tiempoTranscurrido));
		return null;
	}

	@Override
	public String filterType() {
		return "post"; // Palabra clave para filtro POST (manejar response)
	}

	@Override
	public int filterOrder() {
		
		return 0;
	}

}
