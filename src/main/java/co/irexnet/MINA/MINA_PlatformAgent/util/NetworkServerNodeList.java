package co.irexnet.MINA.MINA_PlatformAgent.util;

import co.irexnet.MINA.MINA_PlatformAgent.dto.NetworkServerNodeInfo;
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
public class NetworkServerNodeList
{
    private List<NetworkServerNodeInfo> networkServerNodeList;

    @PostConstruct
    public void init()
    {
        networkServerNodeList = new ArrayList<>();
    }

    public int getSize()
    {
        int nSize = networkServerNodeList.size();
        return nSize;
    }
}
