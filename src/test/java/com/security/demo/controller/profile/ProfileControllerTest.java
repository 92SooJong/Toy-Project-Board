package com.security.demo.controller.profile;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProfileControllerTest {

    @Test
    public void canGetReal1Profile(){

        String expectedProfile = "real1";
        MockEnvironment env = new MockEnvironment();
        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("another-profile");

        ProfileController controller = new ProfileController(env);

        String profile = controller.profile();

        assertThat(profile).isEqualTo(expectedProfile);
    }

//    @Test
//    public void canGetFirstWhenReal1ProfileDoesntExist(){
//        String expectedProfile = "real1";
//        MockEnvironment env = new MockEnvironment();
//
//        env.addActiveProfile(expectedProfile);
//        env.addActiveProfile("real-db");
//
//        ProfileController controller = new ProfileController(env);
//        String profile = controller.profile();
//
//        assertThat(profile).isEqualTo(expectedProfile);
//    }
//
    @Test
    public void canGetDefaultWhenActiveprofileDoesntExist(){

        String expectedProfile = "default";
        MockEnvironment env = new MockEnvironment();
        ProfileController controller = new ProfileController(env);

        String profile = controller.profile();

        assertThat(profile).isEqualTo(expectedProfile);

    }



}