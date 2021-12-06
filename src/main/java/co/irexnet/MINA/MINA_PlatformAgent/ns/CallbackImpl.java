package co.irexnet.MINA.MINA_PlatformAgent.ns;

import java.util.HashMap;
import java.util.Map;

public class CallbackImpl implements ICallback {

	@Override
	public String setCallback(String url, String callbackUrl, String token) 
	{
		String strUrl = url + "/api/v1.0/callback";

		Map<String, String> map = new HashMap<>();
		map.put("url", callbackUrl);
		
		return new RootImpl().send("PUT", strUrl, token, map);
	}

	@Override
	public String getCallback(String url, String token) 
	{
		String strUrl = url + "/api/v1.0/callback";
		return new RootImpl().send("GET", strUrl, token, null);
	}

	@Override
	public String clearCallback(String url, String token) 
	{
		String strUrl = url + "/api/v1.0/callback";
		return new RootImpl().send("DELETE", strUrl, token, null);
	}
}
