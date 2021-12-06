package co.irexnet.MINA.MINA_PlatformAgent.ns;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class RootImpl implements IRoot
{

	@Override
	public int getConnection(String strUrl) 
	{
		StringBuffer response = new StringBuffer();
		int responseCode = 0;
		try
		{
			// Make Trust SSL
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() 
				{
					public X509Certificate[] getAcceptedIssuers()
					{
						return null;
					}
					public void checkClientTrusted(X509Certificate[] certs, String authType){}
					public void checkServerTrusted(X509Certificate[] certs, String authType){}
				}
			};

			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() 
			{
				public boolean verify(String hostname, SSLSession session) 
				{
					return true;
				}
			};

			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
			
			//URL url = new URL(strUrl + "/api/gateway/get-list?token=" + token);
			URL url = new URL(strUrl);
			HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
			
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en:q=0.5");
			
			// Add Request Header
			con.setRequestMethod("GET");
			con.setDoOutput(false);
			
			responseCode = con.getResponseCode();
			System.out.println("\nSending Request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			String inputLine;
			
			
			while((inputLine = in.readLine()) != null)
			{
				response.append(inputLine);
			}
			in.close();
			
			// print result
			//System.out.println(response.toString());
		}
		catch(MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		} 
		catch (KeyManagementException e) 
		{
			e.printStackTrace();
		}
		
		return responseCode;
	}

	public String send(String method, String uri, String token, Map<String, String> body)
	{
		StringBuffer response = new StringBuffer();

		try
		{
			// Make Trust SSL
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager()
			{
				public X509Certificate[] getAcceptedIssuers()
				{
					return null;
				}
				public void checkClientTrusted(X509Certificate[] certs, String authType){}
				public void checkServerTrusted(X509Certificate[] certs, String authType){}
			}
			};

			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier()
			{
				public boolean verify(String hostname, SSLSession session)
				{
					return true;
				}
			};

			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

			URL url = new URL(uri);
			HttpsURLConnection con = (HttpsURLConnection)url.openConnection();

			// Add Request Header
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en:q=0.5");
			con.setRequestProperty("content-type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("token", token);
			con.setRequestMethod(method);

			if(body != null)
			{
				if(body.size() > 0)
				{
					con.setDoOutput(true);

					ObjectMapper objectMapper = new ObjectMapper();
					String strBody = objectMapper.writeValueAsString(body);

					DataOutputStream dos = new DataOutputStream(con.getOutputStream());
					dos.writeBytes(strBody);

					dos.flush();
					dos.close();
				}
				else
				{
					con.setDoOutput(false);
				}
			}
			else
			{
				con.setDoOutput(false);
			}

			int responseCode = con.getResponseCode();
			System.out.println("\nSending Request to URI : " + uri);
			System.out.println("Response Code : " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String strLine;
			while((strLine = br.readLine()) != null)
			{
				response.append(strLine);
			}
			br.close();
		}
		catch(MalformedURLException e)
		{
			response = new StringBuffer();
		}
		catch(IOException e)
		{
			response = new StringBuffer();
		}
		catch (NoSuchAlgorithmException e)
		{
			response = new StringBuffer();
		}
		catch (KeyManagementException e)
		{
			response = new StringBuffer();
		}

		return response.toString();
	}
}
