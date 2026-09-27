package com.bharath.userservice.service;

import com.bharath.userservice.dto.UserDto;
import com.bharath.userservice.entity.User;
import com.bharath.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto createUser(UserDto input){
//        log.info("Creating user:{}",input);
        final User createdUer = User.builder()
                .name(input.name())
                .surname(input.surname())
                .email(input.email())
                .address(input.address())
                .alerting(input.alerting())
                .energyAlertingThreshold(input.energyAlertingThreshold())
                .build();
        final User savedUser = userRepository.save(createdUer);

        return toDto(savedUser);
    }
    private UserDto toDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .address(user.getAddress())
                .email(user.getEmail())
                .surname(user.getSurname())
                .alerting(user.isAlerting())
                .energyAlertingThreshold(user.getEnergyAlertingThreshold())
                .build();
    }

    public UserDto getUserById(Long id){
//        log.info("Getting user by id:{}",id);
        return userRepository.findById(id).map(this::toDto).orElse(null);
    }

    public void updateUser(Long id, UserDto userDto){
//        log.info("Updating the User with id :{}", id);
        User user = userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("User with id:"+id+"does not exist."));
        user.setName(userDto.name());
        user.setSurname(userDto.surname());
        user.setEmail(userDto.email());
        user.setAddress(userDto.address());
        user.setAlerting(userDto.alerting());
        user.setEnergyAlertingThreshold(userDto.energyAlertingThreshold());

        userRepository.save(user);

    }
    public void deleteUser(Long id){
//        log.info("Deleting user with id:{}", id);

        User user
        = userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("User not found"));
        userRepository.delete(user);
    }

}
