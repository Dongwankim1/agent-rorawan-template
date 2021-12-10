package com.agent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NetworkServerNodeLorawanSessionInfo
{
    @JsonProperty("appSKey")
    private String appSKey;

    @JsonProperty("devAddr")
    private String devAddr;

    @JsonProperty("fCntDown")
    private int fCntDown;

    @JsonProperty("fCntUp")
    private int fCntUp;

    @JsonProperty("nwkSKey")
    private String nwkSKey;
}
