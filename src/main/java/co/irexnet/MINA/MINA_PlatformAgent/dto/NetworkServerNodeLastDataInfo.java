package co.irexnet.MINA.MINA_PlatformAgent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class NetworkServerNodeLastDataInfo
{
    @JsonProperty("rssi")
    private int rssi;

    @JsonProperty("mod")
    private String mod;

    @JsonProperty("sf")
    private int sf;

    @JsonProperty("fPort")
    private int fPort;

    @JsonProperty("bw")
    private int bw;

    @JsonProperty("snr")
    private float snr;

    @JsonProperty("freq")
    private String freq;

    @JsonProperty("raw")
    private String raw;

    @JsonProperty("fCnt")
    private int fCnt;

    @JsonProperty("adr")
    private boolean adr;

    @JsonProperty("gateway")
    private List<NetworkServerNodeLastDataGatewayInfo> gateway;

    @JsonProperty("cr")
    private String cr;
}
