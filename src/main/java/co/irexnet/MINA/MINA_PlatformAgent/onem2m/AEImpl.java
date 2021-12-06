package co.irexnet.MINA.MINA_PlatformAgent.onem2m;

import co.irexnet.MINA.MINA_PlatformAgent.util.HttpSend;
import co.irexnet.MINA.MINA_PlatformAgent.util.ResourceType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;

import java.util.HashMap;
import java.util.Map;

public class AEImpl implements IAE
{

    @Override
    public HttpResponse Create(String uri, String cseBase, String oid, String poa, String lbl)
    {
        // Body
        Map<String, String> m2mae = new HashMap<>();
        m2mae.put("lbl", lbl);
        m2mae.put("apn", oid);
        m2mae.put("api", "Non_Registered_App_ID");
        m2mae.put("rr", "false");
        m2mae.put("poa", poa);

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("m2m:ae", m2mae);

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
        HttpPost httpPost = new HttpPost(uri + cseBase);
        StringEntity stringEntity = new StringEntity(strBody, "UTF-8");
        httpPost.setEntity(stringEntity);
        HttpUriRequest request = httpPost;

        request.setHeader("X-M2M-Origin", "SAE-" + oid);
        request.setHeader("X-M2M-RI", "RQI1001");
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-Type", "application/json;ty=" + ResourceType.AE);

        return HttpSend.send(request);
    }

    @Override
    public HttpResponse Retrieve(String uri, String cseBase, String oid)
    {
        // Header
        HttpGet httpGet = new HttpGet(uri + cseBase + "/SAE-" + oid);
        HttpUriRequest request = httpGet;

        request.setHeader("X-M2M-Origin", "admin");
        request.setHeader("X-M2M-RI", "RQI1001");
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-Type", "application/json");

        return HttpSend.send(request);
    }

    @Override
    public HttpResponse UpdatePoa(String uri, String cseBase, String oid, String poa)
    {
        // Body
        Map<String, String> m2mae = new HashMap<>();
        m2mae.put("poa", poa);

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("m2m:ae", m2mae);

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
        HttpPut httpPut = new HttpPut(uri + cseBase + "/SAE-" + oid);
        StringEntity stringEntity = new StringEntity(strBody, "UTF-8");
        httpPut.setEntity(stringEntity);
        HttpUriRequest request = httpPut;

        request.setHeader("X-M2M-Origin", "SAE-" + oid);
        request.setHeader("X-M2M-RI", "RQI1001");
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-Type", "application/json");

        return HttpSend.send(request);
    }

    @Override
    public HttpResponse Delete(String uri, String cseBase, String oid)
    {
        // Header
        HttpDelete httpDelete = new HttpDelete(uri + cseBase + "/SAE-" + oid);
        HttpUriRequest request = httpDelete;

        request.setHeader("X-M2M-Origin", "SAE-" + oid);
        request.setHeader("X-M2M-RI", "RQI1001");
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-Type", "application/json");

        return HttpSend.send(request);
    }
}
