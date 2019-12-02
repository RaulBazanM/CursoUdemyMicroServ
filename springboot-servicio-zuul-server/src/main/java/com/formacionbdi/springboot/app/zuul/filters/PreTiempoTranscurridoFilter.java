package com.formacionbdi.springboot.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component //Para registrarlo como componente, bean
public class PreTiempoTranscurridoFilter extends ZuulFilter{

	// Se añaden logs para consola
	private static Logger log = LoggerFactory.getLogger(PreTiempoTranscurridoFilter.class);
	
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
		log.info(String.format("%s request enrutado a %s", request.getMethod(), request.getRequestURL().toString()));
		
		Long tiempoInicio = System.currentTimeMillis();
		request.setAttribute("tiempoInicio", tiempoInicio);
		return null;
	}

	@Override
	public String filterType() {
		return "pre"; // Palabra clave para filtro PRE (establecer request)
	}

	@Override
	public int filterOrder() {
		
		return 0;
	}

}
