package com.security.demo.domain.user;

import com.security.demo.config.security.ApplicationUserRole;
import com.security.demo.domain.BaseTimeEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "application_user")
public class ApplicationUser extends BaseTimeEntity implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    private String username;
    private String password;
    private String nickname;
    private String role;

    @Builder
    public ApplicationUser(String username, String password, String nickname, String role) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    public ApplicationUser() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        System.out.println("id = " + ApplicationUserRole.valueOf(this.role));

        // TODO - 여기 어떻게 동작하는지?
        return ApplicationUserRole.valueOf(this.role).getGrantedAuthorities();

    }

    public Long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getRole() {
        return role;
    }

    public String getNickname() {
        return nickname;
    }
}
