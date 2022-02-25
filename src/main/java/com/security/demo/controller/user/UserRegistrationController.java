package com.security.demo.controller.user;

import com.security.demo.controller.user.dto.UserSaveRequestDto;
import com.security.demo.service.user.UserRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class UserRegistrationController {


    @GetMapping("/user-registration-form")
    public String registerForm() {
        return "login/userRegistration";
    }


}
