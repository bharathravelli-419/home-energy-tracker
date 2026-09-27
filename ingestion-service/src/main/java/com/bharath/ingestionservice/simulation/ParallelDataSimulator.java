package com.bharath.ingestionservice.simulation;

import com.bharath.ingestionservice.dto.EnergyUsageDto;
import jakarta.annotation.PreDestroy;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Component
public class ParallelDataSimulator implements CommandLineRunner {

    private final ExecutorService executorService;
    @Value("${simulation.parallel-threads}")
    private int parallelThreads;

    private final RestTemplate restTemplate = new RestTemplate();
    private final Random random = new Random();

    @Value("${simulation.requests-per-interval}")
    private int requestsPerInterval;
    @Value("${simulation.ingestion-endpoint}")
    private String ingestionEndpoint;

    public ParallelDataSimulator(){
       this.executorService = Executors.newCachedThreadPool();
    }


    @Override
    public void run(String... args) throws Exception {
        log.info("Parallel Data Simulator started...");
        ((ThreadPoolExecutor)executorService).setCorePoolSize(parallelThreads);

    }

    @Scheduled(fixedRateString = "${simulation.interval-ms}")
    public void sendMockData(){

        int batchSize = requestsPerInterval / parallelThreads;
        int remainder = requestsPerInterval % parallelThreads;

        for(int i=0;i<parallelThreads;i++){
            int requestsPerThread = batchSize + (i< remainder ? 1 : 0);
            executorService.submit(()->{
                for(int j=0;j<requestsPerThread;j++){
                    EnergyUsageDto energyUsageDto = EnergyUsageDto.builder()
                            .deviceId(random.nextLong(1,120))
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
                        log.error("Failed to send data: {} with err: {}",energyUsageDto, e.getMessage());
                    }

                }
            });
        }


    }

    @PreDestroy
    public void shutDown(){
        executorService.shutdown();
        log.info("ParallelDataSimulator is shutting down...");
    }

}
