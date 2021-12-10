package com.agent.service;

import com.agent.dto.*;
import com.agent.ns.*;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class NetworkServerServiceImpl implements INetworkServerService
{
	private String strUri;
	private String strToken;
	
	public NetworkServerServiceImpl(String uri, String token) 
	{
		this.strUri = uri;
		this.strToken = token;
	}
	
	@Override
	public int getConnection() 
	{
		IRoot root = new RootImpl();
		return root.getConnection(strUri);
	}

	@Override
	public void ChangeToken(String token) 
	{
		this.strToken = token;
	}

	@Override
	public NetworkServerGatewaysDTO getGatewayList()
	{
		IGateway gateway = new GatewayImpl();
		String strResponse = gateway.getList(strUri, strToken);
		
		if(strResponse.equalsIgnoreCase("") == false)
		{
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			NetworkServerGatewaysDTO dto;

			try
			{
				dto = objectMapper.readValue(strResponse, NetworkServerGatewaysDTO.class);
			}
			catch(JsonParseException e)
			{
				dto = null;
			}
			catch(IOException e)
			{
				dto = null;
			}

			return dto;
		}
		else
		{
			return null;
		}
	}

	@Override
	public NetworkServerGatewayDTO getGatewayInfo(String gateway_id)
	{
		IGateway gateway = new GatewayImpl();
		String strResponse = gateway.getInfo(strUri, gateway_id, strToken);
		
		if(strResponse.equalsIgnoreCase("") == false)
		{
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			NetworkServerGatewayDTO dto;

			try
			{
				dto = objectMapper.readValue(strResponse, NetworkServerGatewayDTO.class);
			}
			catch(JsonParseException e)
			{
				dto = null;
			}
			catch(IOException e)
			{
				dto = null;
			}

			return dto;
		}
		else
		{
			return null;
		}
	}

	@Override
	public NetworkServerNodesDTO getNodeList()
	{
		INode node = new NodeImpl();
		String strResponse = node.getList(strUri, strToken);
		
		if(strResponse.equalsIgnoreCase("") == false)
		{
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			NetworkServerNodesDTO dto;

			try
			{
				dto = objectMapper.readValue(strResponse, NetworkServerNodesDTO.class);
			}
			catch(JsonParseException e)
			{
				e.printStackTrace();
				dto = null;
			}
			catch(IOException e)
			{
				e.printStackTrace();
				dto = null;
			}

			return dto;
		}
		else
		{
			return null;
		}
	}

	@Override
	public NetworkServerNodeDTO getNodeInfo(String node_id)
	{
		INode node = new NodeImpl();
		String strResponse = node.getInfo(strUri, node_id, strToken);
		
		if(strResponse.equalsIgnoreCase("") == false)
		{
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			NetworkServerNodeDTO dto;

			try
			{
				dto = objectMapper.readValue(strResponse, NetworkServerNodeDTO.class);
			}
			catch(JsonParseException e)
			{
				dto = null;
			}
			catch(IOException e)
			{
				dto = null;
			}

			return dto;
		}
		else
		{
			return null;
		}
	}

	@Override
	public String sendCommand(String node_id, String type, String strCommand) 
	{
		ICommand command = new CommandImpl();
		
		return command.sendCommand(strUri, node_id, type, strCommand, strToken);
	}

	@Override
	public NetworkServerCallbackDTO setCallback(String callbackUri)
	{
		ICallback callback = new CallbackImpl();
		String strResponse = callback.setCallback(strUri, callbackUri, strToken);
		
		if(strResponse.equalsIgnoreCase("") == false)
		{
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			NetworkServerCallbackDTO dto;

			try
			{
				dto = objectMapper.readValue(strResponse, NetworkServerCallbackDTO.class);
			}
			catch(JsonParseException e)
			{
				dto = null;
			}
			catch(IOException e)
			{
				dto = null;
			}

			return dto;
		}
		else
		{
			return null;
		}
	}

	@Override
	public NetworkServerCallbackDTO getCallback() 
	{
		ICallback callback = new CallbackImpl();
		String strResponse = callback.getCallback(strUri, strToken);
		
		if(strResponse.equalsIgnoreCase("") == false)
		{
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			NetworkServerCallbackDTO dto;

			try
			{
				dto = objectMapper.readValue(strResponse, NetworkServerCallbackDTO.class);
			}
			catch(JsonParseException e)
			{
				dto = null;
			}
			catch(IOException e)
			{
				dto = null;
			}

			return dto;
		}
		else
		{
			return null;
		}
	}

	@Override
	public NetworkServerCallbackDTO clearCallback() 
	{
		ICallback callback = new CallbackImpl();
		String strResponse = callback.clearCallback(strUri, strToken);
		
		if(strResponse.equalsIgnoreCase("") == false)
		{
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			NetworkServerCallbackDTO dto;

			try
			{
				dto = objectMapper.readValue(strResponse, NetworkServerCallbackDTO.class);
			}
			catch(JsonParseException e)
			{
				dto = null;
			}
			catch(IOException e)
			{
				dto = null;
			}

			return dto;
		}
		else
		{
			return null;
		}
	}

}
