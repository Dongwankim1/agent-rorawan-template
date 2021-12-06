package co.irexnet.MINA.MINA_PlatformAgent.util;

import co.irexnet.MINA.MINA_PlatformAgent.dto.NetworkServerGatewayInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
@ToString
public class NetworkServerGatewayList
{
    private List<NetworkServerGatewayInfo> networkServerGatewayList;

    @PostConstruct
    public void init()
    {
        networkServerGatewayList = new ArrayList<>();
    }

    public int getSize()
    {
        int nSize = networkServerGatewayList.size();
        return nSize;
    }
}
