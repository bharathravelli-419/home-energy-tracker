package com.bharath.ingestionservice.controller;

import com.bharath.ingestionservice.dto.EnergyUsageDto;
import com.bharath.ingestionservice.service.IngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ingestion")
@RequiredArgsConstructor
public class IngestionController {

    private final IngestionService ingestionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void ingestData(@RequestBody EnergyUsageDto energyUsageDto){
        ingestionService.ingestEnergyUsage(energyUsageDto);
    }



}
