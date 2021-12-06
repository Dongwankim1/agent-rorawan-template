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
public class PropertiesOneM2M
{
    @Value("${properties.onem2m.uri}")
    private String uri;

    @Value("${properties.onem2m.csebase}")
    private String cseBase;

    @Value("${properties.onem2m.poa}")
    private String poa;
}
