package com.bharath.usageservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Setter
@Getter
public class DeviceEnergy{
     Long deviceId;
    Double energyConsumed;
    Long userId;
}