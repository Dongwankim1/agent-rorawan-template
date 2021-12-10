package com.agent.ns;

public interface IGateway
{
	String getList(String url, String token);
	String getInfo(String url, String gateway_id, String token);
}
