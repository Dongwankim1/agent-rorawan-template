package co.irexnet.MINA.MINA_PlatformAgent.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SocketFilter implements Filter{
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		if(req.getLocalPort() == 8090 && req.getRequestURI().equals("/sapi/v1/info")) {
			res.sendError(HttpServletResponse.SC_FORBIDDEN);
		}else {
			chain.doFilter(request, response);
		}
	}
}
