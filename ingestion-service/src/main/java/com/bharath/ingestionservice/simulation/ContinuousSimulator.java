package com.bharath.ingestionservice.simulation;

import com.bharath.ingestionservice.dto.EnergyUsageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;

@Component
@Slf4j
public class ContinuousSimulator implements CommandLineRunner {

    @Value("${simulation.requests-per-interval}")
    private int requestsPerInterval;

    @Value("${simulation.ingestion-endpoint}")
    private String ingestionEndpoint;

    private final RestTemplate restTemplate = new RestTemplate();
    private final Random random = new Random();


    @Override
    public void run(String... args) throws Exception {

        log.info("ContinuousDataSimulator started...");
    }

//    @Scheduled(fixedRateString = "${simulation.interval-ms}" )
    public void sendMockData(){
        for(int i=0;i<requestsPerInterval;i++){
            EnergyUsageDto energyUsageDto = EnergyUsageDto.builder()
                    .deviceId(random.nextLong(1,10))
                    .energyConsumed(Math.round(random.nextDouble(0.0,2.0)*100.0)/100.0)
                    .timeStamp(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
                    .build();

            try{
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<EnergyUsageDto> request = new HttpEntity<>(energyUsageDto,httpHeaders);
                restTemplate.postForEntity(ingestionEndpoint, request, Void.class);
                log.info("sent mock-data: {}", energyUsageDto);

            }
            catch (Exception e){
                log.error("Failed to send data: {}",energyUsageDto);
            }

        }
    }
}
