package co.irexnet.MINA.MINA_PlatformAgent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import co.irexnet.MINA.MINA_PlatformAgent.configure.EmbededTomcatConfiguration;

@SpringBootApplication
@EnableScheduling
@Import(EmbededTomcatConfiguration.class)
public class MinaPlatformAgentApplication extends SpringBootServletInitializer {
	
	 
	public static void main(String[] args) {
		SpringApplication.run(MinaPlatformAgentApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MinaPlatformAgentApplication.class);
	}
	
	
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
	
}
