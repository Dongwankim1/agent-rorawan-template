package com.agent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NetworkServerNodeLorawanInfo
{
    @JsonProperty("devieProfile")
    private String devieProfile;

    @JsonProperty("session")
    private NetworkServerNodeLorawanSessionInfo session;

    @JsonProperty("appKey")
    private String appKey;
}
