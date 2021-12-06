package co.irexnet.MINA.MINA_PlatformAgent.onem2m;

import org.apache.http.HttpResponse;
import org.json.simple.JSONObject;

public interface IContentInstance
{	
	JSONObject CreateFire(String uri, String cseBase,String token, String nodeId,  String message) throws Exception;
    HttpResponse Create(String uri, String cseBase, String oid, String rn, String message);
    HttpResponse Latest(String uri, String cseBase, String oid, String rn);
}
