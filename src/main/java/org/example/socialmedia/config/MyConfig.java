package org.example.socialmedia.config;

import lombok.RequiredArgsConstructor;
import org.example.socialmedia.repository.UserRepository;
import org.example.socialmedia.utils.UtilConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class MyConfig {

    private final UserRepository userRepository;

    private final   MyFilter myFilter;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(myFilter, UsernamePasswordAuthenticationFilter.class);
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable());

        http
                .userDetailsService(userDetailsService());
        http
                .authorizeRequests()
                .requestMatchers(UtilConfig.openPath)
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/product")
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated();
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetailsService userDetailsService =(username)->
             userRepository.findByPhoneNumber(username).orElseThrow(() -> new RuntimeException("User not found"));

        return userDetailsService;
    }
}
