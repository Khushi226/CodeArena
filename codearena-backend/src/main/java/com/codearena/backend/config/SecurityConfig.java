


package com.codearena.backend.config;

import com.codearena.backend.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // Add this
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity // Ensure this is present
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("✅ SECURITY CONFIG LOADED");
        http
            .cors(cors -> {})
            .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/run/**").permitAll()
                        .requestMatchers("/api/problem/**").permitAll()
                        .requestMatchers("/api/learn/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/problems/admin/hidden").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/problems/admin/all").hasRole("ADMIN")
                        .requestMatchers("/problems/**").permitAll()
                        .requestMatchers("/import/**").permitAll()
                        .requestMatchers("/debug/**").permitAll()
                        .requestMatchers("/submissions/**").permitAll()
                        .requestMatchers("/api/learn/problems/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/discuss/**").permitAll()

                        // contests — most specific rules first, Spring uses the first match:
                        .requestMatchers(HttpMethod.POST, "/contests").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/contests/*/problems/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/contests/*/register").authenticated()
                        .requestMatchers(HttpMethod.POST, "/contests/*/submit").authenticated()
                        .requestMatchers(HttpMethod.GET, "/contests/**").permitAll()
                        .requestMatchers("/contests/**").authenticated()

                        // everything else protected
                        .requestMatchers("/submit/**").authenticated()
                        .requestMatchers("/users/me/dashboard").authenticated()
                        .requestMatchers(HttpMethod.POST, "/discuss/**").authenticated()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}