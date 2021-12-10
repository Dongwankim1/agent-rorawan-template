package com.agent.onem2m;

import com.agent.util.HttpSend;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

public class CSEBaseImpl implements ICSEBase
{

    @Override
    public HttpResponse Retrieve(String uri, String cseBase)
    {
        HttpGet httpGet = new HttpGet(uri + cseBase);
        HttpUriRequest request = httpGet;

        request.setHeader("X-M2M-Origin", "admin");
        request.setHeader("X-M2M-RI", "RQI1001");
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-Type", "application/json");

        return HttpSend.send(request);
    }
}
