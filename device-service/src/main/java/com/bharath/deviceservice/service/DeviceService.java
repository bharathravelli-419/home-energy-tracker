package com.bharath.deviceservice.service;

import com.bharath.deviceservice.dto.DeviceDto;
import com.bharath.deviceservice.entity.Device;
import com.bharath.deviceservice.exception.DeviceNotFoundException;
import com.bharath.deviceservice.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceDto getDeviceById(Long id){
        Device device = deviceRepository.findById(id).orElseThrow(()-> new DeviceNotFoundException("Device with id:"+ id+ "is not found."));
        return mapToDto(device);
    }
    public DeviceDto createDevice(DeviceDto input){
        Device device = Device.builder()
                .userId(input.userId())
                .type(input.type())
                .location(input.location())
                .name(input.name())
                .build();
        final Device savedDevice = deviceRepository.save(device);
        return mapToDto(savedDevice);
    }
    private DeviceDto mapToDto(Device device){
        return DeviceDto.builder()
                .userId(device.getUserId())
                .type(device.getType())
                .location(device.getLocation())
                .id(device.getId())
                .name(device.getName())
                .build();
    }
    public DeviceDto updateDevice(Long id, DeviceDto deviceDto) {
        // 1. Fetch the existing record or throw a 404 Exception
        Device existingDevice = deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException("Device with id:"+ id+ "is not found."));

        // 2. Update fields (excluding the ID)
        existingDevice.setName(deviceDto.name());
        existingDevice.setType(deviceDto.type());
        existingDevice.setLocation(deviceDto.location());

        // Handle user relationship update if applicable
        if (deviceDto.userId() != null) {
            existingDevice.setUserId(deviceDto.userId());
        }

        // 3. Save and return updated DTO
        Device updatedDevice = deviceRepository.save(existingDevice);
        return mapToDto(updatedDevice);
    }

    public void deleteDevice(Long id) {
        // 1. Check if the entity exists first to throw a clean exception if it doesn't
        if (!deviceRepository.existsById(id)) {
            throw new DeviceNotFoundException("Device with id:"+ id+ "is not found.");
        }
        // 2. Delete from database
        deviceRepository.deleteById(id);
    }

    public List<DeviceDto> getAllDevicesByUserId(Long userId){
        List<Device> devices = deviceRepository.findAllByUserId(userId);
        return devices.stream().map(this::mapToDto).toList();
    }
}
