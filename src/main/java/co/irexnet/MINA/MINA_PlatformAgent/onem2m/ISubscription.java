package co.irexnet.MINA.MINA_PlatformAgent.onem2m;

import org.apache.http.HttpResponse;

public interface ISubscription
{
    HttpResponse Create(String uri, String cseBase, String oid, String cnt, String nu);
    HttpResponse Retrieve(String uri, String cseBase, String oid, String cnt);
    HttpResponse UpdateNu(String uri, String cseBase, String oid, String cnt, String nu);
    HttpResponse Delete(String uri, String cseBase, String oid, String cnt);
}
