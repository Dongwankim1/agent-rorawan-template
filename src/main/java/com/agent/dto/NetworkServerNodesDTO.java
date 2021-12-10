package com.agent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class NetworkServerNodesDTO
{
	@JsonProperty("errorCode")
	private int errorCode;

	@JsonProperty("errorMsg")
	private String errorMsg;

	@JsonProperty("nodes")
	private List<NetworkServerNodeInfo> nodes;
}
