package com.agent.ns;

public interface INode
{
	String getList(String url, String token);
	String getInfo(String url, String node_id, String token);
}
