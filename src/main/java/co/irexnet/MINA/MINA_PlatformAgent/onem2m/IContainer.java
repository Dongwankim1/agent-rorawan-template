package co.irexnet.MINA.MINA_PlatformAgent.onem2m;

import org.apache.http.HttpResponse;

public interface IContainer
{
    HttpResponse Create(String uri, String cseBase, String oid, String rn, String lbl);
    HttpResponse Retrieve(String uri, String cseBase, String oid, String rn);
    HttpResponse Delete(String uri, String cseBase, String oid, String rn);
}
