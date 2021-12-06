package co.irexnet.MINA.MINA_PlatformAgent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class NetworkServerNodeInfo
{
	@JsonProperty("node_id")
	private String node_id;

	@JsonProperty("node_type")
	private String node_type;

	@JsonProperty("node_desc")
	private String node_desc;

	@JsonProperty("created_at")
	private Date created_at;

	@JsonProperty("bootup_time")
	private Date bootup_time;

	@JsonProperty("connected_gw_id")
	private String connected_gw_id;

	@JsonProperty("last_data")
	private NetworkServerNodeLastDataInfo last_data;

	@JsonProperty("last_timestamp")
	private Date last_timestamp;

	@JsonProperty("lorawan")
	private NetworkServerNodeLorawanInfo lorawan;
}
