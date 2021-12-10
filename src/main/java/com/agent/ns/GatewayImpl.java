package com.agent.ns;

public class GatewayImpl implements IGateway
{

	@Override
	public String getList(String url, String token) 
	{		
		String strUrl = url + "/api/v1.0/gateways";
		
		return new RootImpl().send("GET", strUrl, token, null);
	}
	
	@Override
	public String getInfo(String url, String gateway_id, String token) 
	{
		String strUrl = url + "/api/v1.0/gateway/" + gateway_id;
		
		return new RootImpl().send("GET", strUrl, token, null);
	}
}
