package com.agent.util;

import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseFactory;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicStatusLine;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpSend
{
    public static HttpResponse send(HttpUriRequest request)
    {
        HttpUriRequest httpUriRequest = request;
        HttpResponseFactory factory = new DefaultHttpResponseFactory();
        HttpResponse response = factory.newHttpResponse(
                new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_METHOD_NOT_ALLOWED, null),
                null);

        try
        {
            HttpClient client = HttpClientBuilder.create().build();
            response = client.execute(request);
        }
        catch(HttpHostConnectException e)
        {
            return null;
        }
        catch(ClientProtocolException e)
        {
            return null;
        }
        catch(IOException e)
        {
            return null;
        }

        return response;
    }
    
    public static HttpResponse httpssend(HttpUriRequest request)
    {	
    	SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getInstance("SSL");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	    // set up a TrustManager that trusts everything
	    try {
			sslContext.init(null, new TrustManager[] { new X509TrustManager() {
			            public X509Certificate[] getAcceptedIssuers() {
			                    System.out.println("getAcceptedIssuers =============");
			                    return null;
			            }

			            public void checkClientTrusted(X509Certificate[] certs,
			                            String authType) {
			                    System.out.println("checkClientTrusted =============");
			            }

			            public void checkServerTrusted(X509Certificate[] certs,
			                            String authType) {
			                    System.out.println("checkServerTrusted =============");
			            }
			} }, new SecureRandom());
		} catch (KeyManagementException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(new org.apache.http.conn.ssl.SSLSocketFactory(sslContext)).build();
	    CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(request);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    /*
        HttpUriRequest httpUriRequest = request;
        HttpResponseFactory factory = new DefaultHttpResponseFactory();
        HttpResponse response = factory.newHttpResponse(
                new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_METHOD_NOT_ALLOWED, null),
                null);

        try
        {
            HttpClient client = HttpClientBuilder.create().build();
            response = client.execute(request);
        }
        catch(HttpHostConnectException e)
        {
            return null;
        }
        catch(ClientProtocolException e)
        {
            return null;
        }
        catch(IOException e)
        {
            return null;
        }
		*/
        return response;
    }
}
