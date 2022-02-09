package com.security.demo.service.user;

import com.security.demo.controller.user.dto.UserSaveRequestDto;
import com.security.demo.domain.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public UserRegistrationService(UserRepository userRepo , PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }


    public void registerUser(UserSaveRequestDto userSaveRequestDto) {
        userRepo.save(userSaveRequestDto.toUser(passwordEncoder));
    }



}
