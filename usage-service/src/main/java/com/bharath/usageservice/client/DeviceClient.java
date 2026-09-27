package com.bharath.usageservice.client;

import com.bharath.usageservice.dto.DeviceDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class DeviceClient {

    private final RestTemplate restTemplate;

    private final String baseUrl;

    public DeviceClient(@Value("${device.service.url}") String baseUrl){
        this.baseUrl = baseUrl;
        this.restTemplate = new RestTemplate();
    }

    public DeviceDto getDeviceById(Long deviceId){
        String url = UriComponentsBuilder
                .fromUriString(baseUrl)
                .path("/{deviceId}")
                .buildAndExpand(deviceId)
                .toUriString();
        ResponseEntity<DeviceDto> response = restTemplate.getForEntity(url, DeviceDto.class);

        return response.getBody();
    }

    public List<DeviceDto> getAllDevicesForUser(Long userId){
        String  url = UriComponentsBuilder
                .fromUriString(baseUrl)
                .path("/user/{userId}")
                .buildAndExpand(userId)
                .toUriString();
        ResponseEntity<DeviceDto[]> response = restTemplate.getForEntity(url, DeviceDto[].class);
        DeviceDto[] deviceDtos = response.getBody();
        return deviceDtos == null ? List.of() : List.of(deviceDtos);
    }
}
