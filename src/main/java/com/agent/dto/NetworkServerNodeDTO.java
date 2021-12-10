package com.agent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NetworkServerNodeDTO 
{
	@JsonProperty("errorCode")
	private int errorCode;

	@JsonProperty("errorMsg")
	private String errorMsg;

	@JsonProperty("node")
	private NetworkServerNodeInfo node;
}
