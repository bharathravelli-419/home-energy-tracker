package com.bharath.deviceservice.controller;

import com.bharath.deviceservice.dto.DeviceDto;
import com.bharath.deviceservice.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/device")
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDto> getDeviceById(@PathVariable Long id){
        DeviceDto deviceDto = deviceService.getDeviceById(id);
        return ResponseEntity.ok(deviceDto);
    }

    @PostMapping
    public ResponseEntity<DeviceDto> createDevice(@RequestBody DeviceDto deviceDto){
        DeviceDto createdDevice =  deviceService.createDevice(deviceDto);
        return new ResponseEntity<>(createdDevice, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceDto> updateDevice(@PathVariable Long id, @RequestBody DeviceDto deviceDto) {
        DeviceDto updatedDevice = deviceService.updateDevice(id, deviceDto);
        return ResponseEntity.ok(updatedDevice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DeviceDto>> getAllDevicesByUserId(@PathVariable Long userId){
        List<DeviceDto> deviceDtos = deviceService.getAllDevicesByUserId(userId);
        return ResponseEntity.ok(deviceDtos);
    }

}
