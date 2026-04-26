package com.codearena.backend.controller;

import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codearena.backend.dto.UserProfileResponse;
import com.codearena.backend.entity.User;
import com.codearena.backend.service.UserService;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserProfileResponse me(Authentication authentication) {

        String username = authentication.getName();
        User user = userService.findByUsername(username);

        return new UserProfileResponse(
                user.getUsername(),
                user.getEmail());
    }

    @PutMapping("/users/me/username")
    public UserProfileResponse updateUsername(
            Authentication authentication,
            @RequestBody Map<String, String> body) {

        String currentUsername = authentication.getName();
        String newUsername = body.get("value");

        User user = userService.updateUsername(currentUsername, newUsername);

        return new UserProfileResponse(user.getUsername(), user.getEmail());
    }

    @PutMapping("/users/me/email")
    public UserProfileResponse updateEmail(
            Authentication authentication,
            @RequestBody Map<String, String> body) {

        String username = authentication.getName();
        String newEmail = body.get("value");

        User user = userService.updateEmail(username, newEmail);

        return new UserProfileResponse(user.getUsername(), user.getEmail());
    }

    @PutMapping("/users/me/password")
    public String updatePassword(
            Authentication authentication,
            @RequestBody Map<String, String> body) {

        String username = authentication.getName();
        String newPassword = body.get("value");

        userService.updatePassword(username, newPassword);

        return "Password updated successfully";
    }
}
