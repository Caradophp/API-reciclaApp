package br.com.api.reciclapp.reciclapp.config;

import br.com.api.reciclapp.reciclapp.utils.CustomBasicAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomBasicAuthFilter customBasicAuthFilter;

    public SecurityConfig(CustomBasicAuthFilter customBasicAuthFilter) {
        this.customBasicAuthFilter = customBasicAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("usuarios/login").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(customBasicAuthFilter, BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

}
