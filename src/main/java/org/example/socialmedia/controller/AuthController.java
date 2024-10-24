package org.example.socialmedia.controller;

import lombok.RequiredArgsConstructor;
import org.example.socialmedia.config.JwtProvider;
import org.example.socialmedia.entity.User;
import org.example.socialmedia.payload.UserDTO;
import org.example.socialmedia.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    final JwtProvider jwtProvider;
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;




    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody UserDTO userDTO){

        if (userRepository.existsByPhoneNumber(userDTO.phoneNumber())) {
            throw new RuntimeException("Username is already in use");
        }
        User user = User
                .builder()
                .phoneNumber(userDTO.phoneNumber())
                .password(passwordEncoder.encode(userDTO.password()))
                .name(userDTO.name())
                .username(userDTO.username())

                .build();
        userRepository.save(user);
        return ResponseEntity.ok().body(user);
    }


    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody UserDTO userDTO){
        User user = userRepository.findByPhoneNumber(userDTO.phoneNumber()).orElseThrow(RuntimeException::new);
        if (!passwordEncoder.matches(userDTO.password(),user.getPassword())){
            throw new RuntimeException("Wrong password");
        }

        String token = jwtProvider.generateToken(user);

        return ResponseEntity.ok().body(token);
    }







}
