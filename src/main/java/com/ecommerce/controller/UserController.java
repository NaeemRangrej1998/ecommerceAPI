package com.ecommerce.controller;

import com.ecommerce.dto.LoginRequestDto;
import com.ecommerce.dto.RegistrationDTO;
import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.dto.response.JwtResponseDto;
import com.ecommerce.dto.response.LoginResponseDTO;
import com.ecommerce.dto.response.UserInfoDTO;
import com.ecommerce.entity.UserEntity;
import com.ecommerce.exception.CustomException;
import com.ecommerce.service.UserService;
import com.ecommerce.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;


    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/singin")
    public ResponseEntity<ApiResponse> singInUser(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            JwtResponseDto jwtResponseDto = userService.singIn(loginRequestDto);
            System.out.println("jwtResponseDto.getToken() = " + jwtResponseDto.getToken());
//            String jwtToken = jwtService.generateToken(authenticatedUser);

//            LoginResponseDTO loginResponse = new LoginResponseDTO();
//            loginResponse.setToken(jwtToken);
//            loginResponse.setExpiresIn(jwtService.getExpirationTime());
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "Sign in Success", jwtResponseDto));
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Something Went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }


//        UserEntity userEntity=userService.loginUser(loginRequestDto);
//        return ResponseEntity.ok(userEntity);
    }

    @PostMapping("/addUser")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody RegistrationDTO userRegisterRequest) {
        try {
            RegistrationDTO registrationDTO = userService.registerUser(userRegisterRequest);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "SignUp in Success", registrationDTO));
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Something Went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUser")
    public ResponseEntity<ApiResponse> getAllUsers() {
        try {
            List<UserInfoDTO> infoDTO = userService.getAllUsers();
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "Get All Users", infoDTO));
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Something Went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/Name")
    public String getName() {
        return "Naim";
    }
}
