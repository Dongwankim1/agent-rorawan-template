package co.irexnet.MINA.MINA_PlatformAgent.util;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="properties")
@Getter
@ToString
public class PropertiesNetworkServer
{
    @Value("${properties.network_server.token}")
    private String token;

    @Value("${properties.network_server.uri}")
    private String uri;

    @Value("${properties.network_server.callback}")
    private String callback;
}
