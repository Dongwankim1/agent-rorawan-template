package com.agent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NetworkServerNodeLastDataGatewayInfo
{
    @JsonProperty("mac")
    private String mac;

    @JsonProperty("rssi")
    private int rssi;

    @JsonProperty("snr")
    private float snr;
}
