package co.irexnet.MINA.MINA_PlatformAgent.configure;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

@Configuration
public class FiltersConfiguration {
	@Bean
	public FilterRegistrationBean getFilterRegistrationBean() {
		
		Filter filter = new OncePerRequestFilter() {
			
			@Override
			protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
					throws ServletException, IOException {
				HttpServletRequest req = (HttpServletRequest) request;
				HttpServletResponse res = (HttpServletResponse) response;
				if(req.getLocalPort() == 8091 && !(req.getRequestURI().equals("/sapi/v1/info"))) {
					res.sendError(HttpServletResponse.SC_FORBIDDEN);
				}else {
					filterChain.doFilter(request, response);
				}
			}
		};
		
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(filter);
		filterRegistrationBean.setOrder(-100);
		
		return filterRegistrationBean; 
		
	}
}
