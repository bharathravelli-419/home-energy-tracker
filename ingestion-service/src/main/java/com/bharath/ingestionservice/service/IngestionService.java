package com.bharath.ingestionservice.service;

import com.bharath.ingestionservice.dto.EnergyUsageDto;
import com.bharath.kafka.event.EnergyUsageEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class IngestionService {
    private final KafkaTemplate<String, EnergyUsageEvent> kafkaTemplate;

    public void ingestEnergyUsage(EnergyUsageDto energyUsageDto){
        EnergyUsageEvent event = EnergyUsageEvent.builder()
                .deviceId(energyUsageDto.deviceId())
                .energyConsumed(energyUsageDto.energyConsumed())
                .timeStamp(energyUsageDto.timeStamp())
                .build();
        kafkaTemplate.send("energy-usage", event);
        log.info("Ingested Energy Usage Event: {}",event);
    }
}
