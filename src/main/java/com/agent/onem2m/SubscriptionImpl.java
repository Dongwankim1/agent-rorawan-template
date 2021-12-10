package com.agent.onem2m;

import com.agent.util.HttpSend;
import com.agent.util.ResourceType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;

import java.util.HashMap;
import java.util.Map;

public class SubscriptionImpl implements ISubscription
{

    @Override
    public HttpResponse Create(String uri, String cseBase, String oid, String cnt, String nu)
    {
        // Body
        Map<String, String> jsonEnc = new HashMap<>();
        jsonEnc.put("net", "3");

        Map<String, Object> m2msub = new HashMap<>();
        m2msub.put("rn", "sub-" + oid);
        m2msub.put("nu", nu);
        m2msub.put("enc", jsonEnc);

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("m2m:sub", m2msub);

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
        HttpPost httpPost = new HttpPost(uri + cseBase + "/SAE-" + oid + "/cnt-" + cnt);
        StringEntity stringEntity = new StringEntity(strBody, "UTF-8");
        httpPost.setEntity(stringEntity);
        HttpUriRequest request = httpPost;

        request.setHeader("X-M2M-Origin", "SAE-" + oid);
        request.setHeader("X-M2M-RI", "RQI1001");
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-Type", "application/json;ty=" + ResourceType.subscription);

        return HttpSend.send(request);
    }

    @Override
    public HttpResponse Retrieve(String uri, String cseBase, String oid, String cnt)
    {
        // Header
        HttpGet httpGet = new HttpGet(uri + cseBase + "/SAE-" + oid + "/cnt-" + cnt + "/sub-" + oid);
        HttpUriRequest request = httpGet;

        request.setHeader("X-M2M-Origin", "SAE-" + oid);
        request.setHeader("X-M2M-RI", "RQI1001");
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-Type", "application/json");

        return HttpSend.send(request);
    }

    @Override
    public HttpResponse UpdateNu(String uri, String cseBase, String oid, String cnt, String nu)
    {
        // Body
        Map<String, String> m2msub = new HashMap<>();
        m2msub.put("nu", nu);

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("m2m:sub", m2msub);

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
        HttpPut httpPut = new HttpPut(uri + cseBase + "/SAE-" + oid + "/cnt-" + cnt + "/sub-" + oid);
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
    public HttpResponse Delete(String uri, String cseBase, String oid, String cnt)
    {
        // Header
        HttpDelete httpDelete = new HttpDelete(uri + cseBase + "/SAE-" + oid + "/cnt-" + cnt + "/sub-" + oid);
        HttpUriRequest request = httpDelete;

        request.setHeader("X-M2M-Origin", "SAE-" + oid);
        request.setHeader("X-M2M-RI", "RQI1001");
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-Type", "application/json");

        return HttpSend.send(request);
    }
}
