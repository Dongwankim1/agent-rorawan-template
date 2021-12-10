package com.agent.ns;

import java.util.HashMap;
import java.util.Map;

public class CommandImpl implements ICommand {

	@Override
	public String sendCommand(String url, String node_id, String type, String command, String token) 
	{
		String strUrl = url + "/api/v1.0/command";

		Map<String, String> map = new HashMap<>();
		map.put("nid", node_id);
		map.put("type", type);
		map.put("command", command);
		
		return new RootImpl().send("POST", strUrl, token, map);
	}
}
