package com.agent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class NetworkServerGatewayInfo
{
	@JsonProperty("gateway_id")
	private String gateway_id;

	@JsonProperty("gateway_type")
	private String gateway_type;

	@JsonProperty("gateway_desc")
	private String gateway_desc;

	@JsonProperty("gateway_eui")
	private String gateway_eui;

	@JsonProperty("lora_type")
	private boolean lora_type;

	@JsonProperty("created_at")
	private Date created_at;

	@JsonProperty("recent_act")
	private Date recent_act;
}
