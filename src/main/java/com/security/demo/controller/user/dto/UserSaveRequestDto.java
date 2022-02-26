package com.security.demo.controller.user.dto;

import com.security.demo.domain.user.ApplicationUser;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class UserSaveRequestDto {

    private String username;
    private String nickname;
    private String password;

    @Builder
    public UserSaveRequestDto(String username, String nickname, String password) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
    }

    public ApplicationUser toUser(PasswordEncoder passwordEncoder) {

        return ApplicationUser.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .role("USER") // role은 USER로 하드코딩
                .build();
    }


}
