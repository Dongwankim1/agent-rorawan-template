package co.irexnet.MINA.MINA_PlatformAgent.onem2m;

import org.apache.http.HttpResponse;

public interface ICSEBase
{
    HttpResponse Retrieve(String uri, String cseBase);
}
