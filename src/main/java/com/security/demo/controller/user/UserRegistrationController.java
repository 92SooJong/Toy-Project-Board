package com.security.demo.controller.user;

import com.security.demo.controller.user.dto.UserSaveRequestDto;
import com.security.demo.service.user.UserRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user-registration")
public class UserRegistrationController {

    private UserRegistrationService userRegistrationService;

    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @GetMapping
    public String registerForm(Model model) {
        model.addAttribute("userSaveRequestDto" , new UserSaveRequestDto());
        return "/login/userRegistration";
    }

    @PostMapping
    public String processRegistration(@ModelAttribute UserSaveRequestDto userSaveRequestDto) {

        userRegistrationService.registerUser(userSaveRequestDto);
        return "redirect:/login";
    }

}
