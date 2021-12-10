package com.agent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NetworkServerNotifyDataDTO {

    @JsonProperty("raw")
    String raw;
    @JsonProperty("rssi")
    int rssi;
    @JsonProperty("snr")
    int snr;
    @JsonProperty("fCnt")
    int fcnt;
    @JsonProperty("fPort")
    int fport;
    @JsonProperty("freq")
    int freq;
    @JsonProperty("mod")
    String mod;
    @JsonProperty("bw")
    int bw;
    @JsonProperty("adr")
    boolean adr;
    @JsonProperty("sf")
    int sf;
    @JsonProperty("cr")
    String cr;
    List<NetworkServerNotifyGatewayDTO> gateway;








}
