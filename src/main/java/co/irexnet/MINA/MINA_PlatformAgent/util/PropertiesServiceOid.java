package co.irexnet.MINA.MINA_PlatformAgent.util;

import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix="properties")
@Getter
@ToString
public class PropertiesServiceOid
{
    private List<String> service_oid = new ArrayList<>();
}
