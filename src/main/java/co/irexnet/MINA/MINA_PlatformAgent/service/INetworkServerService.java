package co.irexnet.MINA.MINA_PlatformAgent.service;

import co.irexnet.MINA.MINA_PlatformAgent.dto.*;

public interface INetworkServerService
{
	int getConnection();
	public void ChangeToken(String token);
	NetworkServerGatewaysDTO getGatewayList();
	NetworkServerGatewayDTO getGatewayInfo(String gateway_id);
	
	NetworkServerNodesDTO getNodeList();
	NetworkServerNodeDTO getNodeInfo(String node_id);
	
	String sendCommand(String node_id, String type, String command);
	
	NetworkServerCallbackDTO setCallback(String callbackUri);
	NetworkServerCallbackDTO getCallback();
	NetworkServerCallbackDTO clearCallback();
}
