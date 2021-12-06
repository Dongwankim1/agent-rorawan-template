package co.irexnet.MINA.MINA_PlatformAgent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class NetworkServerNotifyDTO {

    @JsonProperty("type")
    String type;

    @JsonProperty("gid")
    String gateWayid;

    @JsonProperty("nid")
    String nodeId;

    @JsonProperty("data")
    NetworkServerNotifyDataDTO data;


    

}
