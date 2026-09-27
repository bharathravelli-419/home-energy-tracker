package com.bharath.deviceservice.dto;

import com.bharath.deviceservice.model.DeviceType;
import lombok.Builder;

@Builder
public record DeviceDto(
        String name, DeviceType type, Long id, String location, Long userId
) {
}
