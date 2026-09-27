package com.bharath.insightservice.service;

import com.bharath.insightservice.client.UsageClient;
import com.bharath.insightservice.dto.DeviceDto;
import com.bharath.insightservice.dto.InsightDto;
import com.bharath.insightservice.dto.UsageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

@Service
@Slf4j
//@RequiredArgsConstructor
public class InsightService {

    private final UsageClient usageClient;
    private OllamaChatModel ollamaChatModel;

    public InsightService(UsageClient usageClient, OllamaChatModel ollamaChatModel){
        this.ollamaChatModel = ollamaChatModel;
        this.usageClient = usageClient;
    }

    public InsightDto getOverview(Long userId){

        final UsageDto usageData = usageClient.getXDaysUsageForUser(userId,3);

        double totalUsage = usageData.devices().stream()
                .mapToDouble(DeviceDto::energyConsumed)
                .sum();
        log.info(" Calling Ollama for userId {} with total usage {}", userId, totalUsage);
        String prompt = new StringBuilder()
                .append("Analyse the following Energy usage data and provide a concise overview with actionable insights.")
                .append("This data is the aggregate data for the past 3 days.")
                .append("Usage data: \n")
                .append(usageData.devices())
                .toString();
        ChatResponse chatResponse = ollamaChatModel.call(
                Prompt.builder().content(prompt).build()

        );
        return InsightDto.builder()
                .userId(userId)
                .tips(chatResponse.getResult().getOutput().getText())
                .energyUsage(totalUsage)
                .build();

    }

    public InsightDto getSavingTips (Long userId) {
        // Fetch data from Usage Service
        final UsageDto usageData = usageClient.getXDaysUsageForUser(userId, 3);

        double totalUsage = usageData.devices().stream()
                .mapToDouble(DeviceDto::energyConsumed)
                .sum();

        log.info ("Calling Ollama for userId {} with total usage {}",
                userId, totalUsage);

        String prompt = new StringBuilder()
                .append("This is my total consumption over the past 3 days.")
                .append("How can I reduce my energy consumption? How does it compare to average households?")
                .append("Total energu used: \n")
                .append(totalUsage)
                .toString();

        ChatResponse response = ollamaChatModel.call(
                Prompt.builder()
                        .content(prompt)
                        .build());

        return InsightDto.builder()
                .userId(userId)
                .tips(response.getResult().getOutput().getText())
                .energyUsage(totalUsage)
                .build();
    }
}
