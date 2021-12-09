package co.irexnet.MINA.MINA_PlatformAgent.configure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties
public class EmbededTomcatConfiguration{
	@Value("${server.socket-port:0}")
	private Integer serverHttpPort;
	
	public class TomcatWebServerHttpPortCustomizer
    	implements WebServerFactoryCustomizer {
		
		@Override
		public void customize(WebServerFactory factory) {
			
				
			    if (serverHttpPort > 0) {
		
			        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
			        connector.setPort(serverHttpPort);
			        
			        ((TomcatServletWebServerFactory) factory).addAdditionalTomcatConnectors(connector);
			    }
			}
		}
	 	@Bean
	    public WebServerFactoryCustomizer containerCustomizer() {
	        return new TomcatWebServerHttpPortCustomizer();
	    }

	
	
}
