package co.irexnet.MINA.MINA_PlatformAgent.onem2m;


import co.irexnet.MINA.MINA_PlatformAgent.util.HttpSend;
import co.irexnet.MINA.MINA_PlatformAgent.util.ResourceType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import java.util.HashMap;
import java.util.Map;

public class ContainerImpl implements IContainer
{
    @Override
    public HttpResponse Create(String uri, String cseBase, String oid, String rn, String lbl)
    {
        // Body
        Map<String, String> m2mcnt = new HashMap<>();
        m2mcnt.put("rn", "cnt-" + rn);
        m2mcnt.put("lbl", lbl);

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("m2m:cnt", m2mcnt);

        ObjectMapper objectMapper = new ObjectMapper();
        String strBody = "";

        try
        {
            strBody = objectMapper.writeValueAsString(jsonBody);
        }
        catch(JsonProcessingException e)
        {
            e.printStackTrace();
        }

        // Header
        HttpPost httpPost = new HttpPost(uri + cseBase + "/SAE-" + oid);
        StringEntity stringEntity = new StringEntity(strBody, "UTF-8");
        httpPost.setEntity(stringEntity);
        HttpUriRequest request = httpPost;

        request.setHeader("X-M2M-Origin", "SAE-" + oid);
        request.setHeader("X-M2M-RI", "RQI1001");
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-Type", "application/json;ty=" + ResourceType.container);

        return HttpSend.send(request);
    }

    @Override
    public HttpResponse Retrieve(String uri, String cseBase, String oid, String rn)
    {
        // Header
        HttpGet httpGet = new HttpGet(uri + cseBase + "/SAE-" + oid + "/cnt-" + rn);
        HttpUriRequest request = httpGet;

        request.setHeader("X-M2M-Origin", "SAE-" + oid);
        request.setHeader("X-M2M-RI", "RQI1001");
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-Type", "application/json");

        return HttpSend.send(request);
    }

    @Override
    public HttpResponse Delete(String uri, String cseBase, String oid, String rn)
    {
        // Header
        HttpDelete httpDelete = new HttpDelete(uri + cseBase + "/SAE-" + oid + "/cnt-" + rn);
        HttpUriRequest request = httpDelete;

        request.setHeader("X-M2M-Origin", "SAE-" + oid);
        request.setHeader("X-M2M-RI", "RQI1001");
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-Type", "application/json");

        return HttpSend.send(request);
    }
}
