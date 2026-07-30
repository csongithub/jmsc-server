/**
 * 
 */
package com.jmsc.app.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chandan
 *
 */
@Component
@Slf4j
public class CORSFilter implements Filter{

	// @formatter:off
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;
		
		//log.debug("***************** CORSFileter called, with method: " + request.getMethod() + ", url: " + request.getRequestURL());
		
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, X-Authorization, Authorization, authorization, Content-Disposition");
        response.setHeader("Access-Control-Expose-Headers", "Location");
        response.setHeader("Content-Type", "application/octet-stream");
        
		if ("OPTIONS".equals(request.getMethod())) {
			chain.doFilter(req, res);
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			Assert.isTrue(request instanceof HttpServletRequest, "This is Http Request");
			HttpServletRequest httpServletRequest = HttpServletRequest.class.cast(request);
			String uri = httpServletRequest.getRequestURI();
			String query = httpServletRequest.getQueryString();
			if (query != null) {
				uri = uri + "?" + query;
			}
			log.info("New request for {}", uri);
			long startTime = System.currentTimeMillis();
			chain.doFilter(request, response);
			long timeTaken = System.currentTimeMillis() - startTime;
			log.info("Request for {} took {} ms.", uri, timeTaken);
		}
	}
}
