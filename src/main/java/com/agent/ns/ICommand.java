package com.agent.ns;

public interface ICommand
{
	String sendCommand(String url, String node_id, String type, String command, String token);
}
