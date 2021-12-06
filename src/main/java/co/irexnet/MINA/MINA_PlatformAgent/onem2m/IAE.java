package co.irexnet.MINA.MINA_PlatformAgent.onem2m;

import org.apache.http.HttpResponse;

public interface IAE
{
    HttpResponse Create(String uri, String cseBase, String oid, String poa, String lbl);
    HttpResponse Retrieve(String uri, String cseBase, String oid);
    HttpResponse UpdatePoa(String uri, String cseBase, String oid, String poa);
    HttpResponse Delete(String uri, String cseBase, String oid);
}
