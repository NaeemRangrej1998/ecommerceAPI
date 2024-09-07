package com.ecommerce.controller;

import com.ecommerce.dto.LoginRequestDto;
import com.ecommerce.dto.RegistrationDTO;
import com.ecommerce.dto.response.LoginResponseDTO;
import com.ecommerce.entity.UserEntity;
import com.ecommerce.service.UserService;
import com.ecommerce.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        UserEntity authenticatedUser = userService.loginUser(loginRequestDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponseDTO loginResponse = new LoginResponseDTO();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
//        UserEntity userEntity=userService.loginUser(loginRequestDto);
//        return ResponseEntity.ok(userEntity);
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationDTO> registerUser(@RequestBody RegistrationDTO userRegisterRequest) {
        RegistrationDTO registrationDTO = userService.registerUser(userRegisterRequest);
        return ResponseEntity.ok(registrationDTO);
    }

    @GetMapping("/Name")
    public String getName() {
        return "Naim";
    }
}
