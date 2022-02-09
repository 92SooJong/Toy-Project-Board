package com.security.demo.controller.user.dto;

import com.security.demo.domain.user.ApplicationUser;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class UserSaveRequestDto {

    private String username;
    private String nickname;
    private String password;

    public ApplicationUser toUser(PasswordEncoder passwordEncoder) {

        return ApplicationUser.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .nickName(nickname)
                .role("USER") // role은 USER로 하드코딩
                .build();
    }


}
