package com.security.demo.controller.user;


import com.security.demo.controller.user.dto.UserSaveRequestDto;
import com.security.demo.service.user.UserRegistrationService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRegistrationApiController {

    private final UserRegistrationService userRegistrationService;

    public UserRegistrationApiController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping("/api/v1/user")
    public Long processRegistration(@RequestBody UserSaveRequestDto userSaveRequestDto) {
        return userRegistrationService.registerUser(userSaveRequestDto);
    }

}
