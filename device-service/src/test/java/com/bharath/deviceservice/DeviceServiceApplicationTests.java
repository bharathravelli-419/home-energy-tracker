package com.bharath.deviceservice;

import com.bharath.deviceservice.entity.Device;
import com.bharath.deviceservice.model.DeviceType;
import com.bharath.deviceservice.repository.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class DeviceServiceApplicationTests {

    @Autowired
    private DeviceRepository deviceRepository;
	@Test
	void contextLoads() {
	}

    @Disabled
    @Test
    void createDevices(){
        for(int i=1;i<=200;i++){
            Device device = Device.builder()
                    .name("Device"+i)
                    .type(DeviceType.values()[i % DeviceType.values().length])
                    .location("Location "+ i)
                    .userId((long)i%10)
                    .build();
            deviceRepository.save(device);
        }
        log.info("device repo has been populated.");
    }


}
