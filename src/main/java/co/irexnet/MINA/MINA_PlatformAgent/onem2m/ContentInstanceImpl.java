package co.irexnet.MINA.MINA_PlatformAgent.onem2m;

import co.irexnet.MINA.MINA_PlatformAgent.util.HttpSend;
import co.irexnet.MINA.MINA_PlatformAgent.util.PropertiesNetworkServer;
import co.irexnet.MINA.MINA_PlatformAgent.util.ResourceType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mysql.cj.xdevapi.JsonParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ContentInstanceImpl implements IContentInstance
{	
	@Autowired
    PropertiesNetworkServer propertiesNetworkServer;

	@Override
	public JSONObject CreateFire(String uri, String cseBase, String token, String nodeId, String message) throws Exception {
		// TODO Auto-generated method stub
		   URL obj = new URL(uri+"/api/v1.0/command");
	        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
	        
	        /************************ 인증서 적용 후 제거 할 것 START **********************/
	        con.setHostnameVerifier(new HostnameVerifier() {        
	            public boolean verify(String hostname, SSLSession session)  {  
	            return true;
	            }
	        });
	        /************************ 인증서  적용 후 제거 할 것 END **********************/
	        
	        //add reuqest header
	        con.setRequestMethod("POST");
	        con.setRequestProperty("token", token);
	        con.setRequestProperty("Accept", "application/json");
	        con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
	        con.setUseCaches(false);
	        con.setDoInput(true);
	        con.setDoOutput(true);
	        Map<String, String> data = new HashMap<>();
			 data.put("nid",nodeId);
			 data.put("type", "hex");
			 data.put("command", message);
	        
	        Gson g = new Gson();
	        String json = g.toJson(data);
	        
	  
	        OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
	        out.write(json.toString());
	        out.close();
	 
	        int responseCode = con.getResponseCode();
	        System.out.println("\nSending 'POST' request to URL : " + uri);
	        System.out.println("Post parameters : " + json);
	        System.out.println("Response Code : " + responseCode);
	        
	        if(responseCode != 200){
	            System.out.println("연결 실패");
	        }
	        
	        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();
	 
	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();
	        System.out.println(response.toString());
	        //response 값은 "{code:200, Agent:{ID:12398723418974}}" 형식
	      //response 값은 "{code:200, Agent:{ID:12398723418974}}" 형식
	        JSONParser parser = new JSONParser();
	        Object jsondata = parser.parse( response.toString() );
	        
	        JSONObject jsonObj = (JSONObject)jsondata;
	        
	        
	        return jsonObj;
	}
	
	
	
    @Override
    public HttpResponse Create(String uri, String cseBase, String oid, String rn, String message)
    {
        // Body
        Map<String, String> m2mcin = new HashMap<>();
        m2mcin.put("cnf", "application/json");
        m2mcin.put("con", message);

        Map<String, Object> json = new HashMap<>();
        json.put("m2m:cin", m2mcin);

        ObjectMapper objectMapper = new ObjectMapper();
        String strBody = "";

        try
        {
            strBody = objectMapper.writeValueAsString(json);
        }
        catch(JsonProcessingException e)
        {
            e.printStackTrace();
        }

        // Header
        HttpPost httpPost = new HttpPost(uri + cseBase + "/SAE-" + oid + "/cnt-" + rn);
        StringEntity stringEntity = new StringEntity(strBody, "UTF-8");
        httpPost.setEntity(stringEntity);
        HttpUriRequest request = httpPost;

        request.setHeader("X-M2M-Origin", "SAE-" + oid);
        request.setHeader("X-M2M-RI", "RQI1001");
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-Type", "application/json;ty=" + ResourceType.contentInstance);

        return HttpSend.send(request);
    }

    @Override
    public HttpResponse Latest(String uri, String cseBase, String oid, String rn)
    {
        // Header
        HttpGet httpGet = new HttpGet(uri + cseBase + "/SAE-" + oid + "/cnt-" + rn + "/la");
        HttpUriRequest request = httpGet;

        request.setHeader("X-M2M-Origin", "SAE-" + oid);
        request.setHeader("X-M2M-RI", "RQI1001");
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-Type", "application/json");

        return HttpSend.send(request);
    }
}
