package co.irexnet.MINA.MINA_PlatformAgent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class NetworkServerGatewaysDTO
{
	@JsonProperty("errorCode")
	private int errorCode;

	@JsonProperty("errorMsg")
	private String errorMsg;

	@JsonProperty("gateways")
	private List<NetworkServerGatewayInfo> gateways;
}
