

package com.codearena.backend.controller;

import com.codearena.backend.dto.AuthResponse;
import com.codearena.backend.dto.LoginRequest;
import com.codearena.backend.dto.SignupRequest;
import com.codearena.backend.entity.User;
import com.codearena.backend.repository.UserRepository;
import com.codearena.backend.security.JwtUtil;
import com.codearena.backend.service.UserService;
import com.codearena.backend.service.PasswordResetService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

        private final UserService userService;
        private final AuthenticationManager authenticationManager;
        private final JwtUtil jwtUtil;
        private final UserRepository userRepository;
        private final PasswordResetService passwordResetService;
        private final PasswordEncoder passwordEncoder;

        public AuthController(UserService userService,
                        AuthenticationManager authenticationManager,
                        JwtUtil jwtUtil,
                        UserRepository userRepository,
                        PasswordResetService passwordResetService,
                        PasswordEncoder passwordEncoder) {

                this.userService = userService;
                this.authenticationManager = authenticationManager;
                this.jwtUtil = jwtUtil;
                this.userRepository = userRepository;
                this.passwordResetService = passwordResetService;
                this.passwordEncoder = passwordEncoder;
        }

        // ================================
        // Signup API
        // ================================
        @PostMapping("/signup")
        public ResponseEntity<?> signup(@RequestBody SignupRequest request) {

                System.out.println("✅ SIGNUP API HIT");

                userService.register(request);

                return ResponseEntity.ok(
                                Map.of("message", "Signup successful"));
        }

        // ================================
        // Login API
        // ================================
        @PostMapping("/login")
        public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getUsername(),
                                                request.getPassword()));

                User user = userRepository.findByUsername(authentication.getName())
                                .orElseThrow(() -> new RuntimeException("User not found"));

                String token = jwtUtil.generateToken(user.getUsername(), user.getId());

                AuthResponse response = new AuthResponse();
                response.setUsername(user.getUsername());
                response.setUserId(user.getId());
                response.setToken(token);
                response.setRole(user.getRole());

                return ResponseEntity.ok(response);
        }

        // ================================
        // Forgot Password - Send OTP
        // ================================
        @PostMapping("/forgot-password")
        public ResponseEntity<?> forgotPassword(@RequestParam String email) {

                Optional<User> user = userRepository.findByEmail(email);

                if (user.isEmpty()) {
                        return ResponseEntity.badRequest().body("User not found");
                }

                passwordResetService.sendOtp(email);

                return ResponseEntity.ok("OTP sent to email");
        }

        // ================================
        // Verify OTP
        // ================================
        @PostMapping("/verify-otp")
        public ResponseEntity<?> verifyOtp(
                        @RequestParam String email,
                        @RequestParam String otp) {

                boolean isValid = passwordResetService.verifyOtp(email, otp);

                if (!isValid) {
                        return ResponseEntity.badRequest().body("Invalid or expired OTP");
                }

                return ResponseEntity.ok("OTP verified");
        }

        // ================================
        // Reset Password
        // ================================
        @PostMapping("/reset-password")
        public ResponseEntity<?> resetPassword(
                        @RequestParam String email,
                        @RequestParam String newPassword) {

                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new RuntimeException("User not found"));

                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);

                return ResponseEntity.ok("Password updated successfully");
        }
}