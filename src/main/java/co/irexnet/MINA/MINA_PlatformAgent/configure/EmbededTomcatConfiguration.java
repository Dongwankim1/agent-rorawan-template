package co.irexnet.MINA.MINA_PlatformAgent.configure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class EmbededTomcatConfiguration implements WebMvcConfigurer{
	@Value("${server.port}")
	private String serverPort;
	
	@Value("${server.addtionalPorts}:null")
	private String additionaPorts;
	
	@Bean
	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer(){
		return factory ->{
			Connector[] additionalConnectors = additionalConnector();
			if(additionalConnectors.length>0) {
				factory.addAdditionalTomcatConnectors(additionalConnectors);
			}
		};
	}
	
	private Connector[] additionalConnector() {
		if(this.additionaPorts == null || this.additionaPorts.equals("")) {
			return new Connector[0];
		}
		
		Set<String> defaultPorts = new HashSet<>(Arrays.asList(this.serverPort));
		
		String[] ports = this.additionaPorts.split(",");
		List<Connector> result = new ArrayList<>();
		for(String port :ports) {
			if(StringUtils.hasText(port) && !"null".equalsIgnoreCase(port) && !defaultPorts.contains(ports)){
				Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
				connector.setScheme("http");
				connector.setPort(Integer.parseInt(port.trim()));
				
			}
		}
		
		return result.toArray(new Connector[] {});
	}
	
	
}
