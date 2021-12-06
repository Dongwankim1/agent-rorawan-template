package co.irexnet.MINA.MINA_PlatformAgent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NetworkServerGatewayDTO
{
	@JsonProperty("errorCode")
	private int errorCode;

	@JsonProperty("errorMsg")
	private String errorMsg;

	@JsonProperty("gateway")
	private NetworkServerGatewayInfo gateway;
}
