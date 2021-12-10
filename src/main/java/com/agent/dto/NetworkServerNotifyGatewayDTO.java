package com.agent.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
public class NetworkServerNotifyGatewayDTO {
    String mac;
    int rssi;
    int snr;
    BigDecimal latitude;
    BigDecimal longitude;
    int altitude;
}
