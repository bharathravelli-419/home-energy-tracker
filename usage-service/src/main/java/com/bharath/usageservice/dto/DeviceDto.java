package com.bharath.usageservice.dto;

import lombok.Builder;

@Builder
public record DeviceDto(
        String name, String type, Long id, String location, Long userId, Double energyConsumed
) {
}