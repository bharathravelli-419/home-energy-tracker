package com.bharath.userservice;

import com.bharath.userservice.entity.User;
import com.bharath.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class UserServiceApplicationTests {

    @Autowired
    private UserRepository userRepository;

	@Test
	void contextLoads() {
	}

    @Disabled
    @Test
    void createUsers(){

        for (int i = 1; i <= 20; i++) {
            User user = User.builder()
                    .name("User" + i)
                    .surname("Surname" + i)
                    .email("user" + i + "@example.com")
                    .address("Street " + i + ", City " + i)
                    .alerting(Math.random() > 0.5) // Random true/false
                    .energyAlertingThreshold(Math.random() * 100) // Random double between 0.0 and 100.0
                    .build();

            userRepository.save(user);


        }
        log.info("Users have been populated.");
    }

}
