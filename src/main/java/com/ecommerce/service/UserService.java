package com.ecommerce.service;

import com.ecommerce.dto.LoginRequestDto;
import com.ecommerce.dto.RegistrationDTO;
import com.ecommerce.dto.response.JwtResponseDto;
import com.ecommerce.dto.response.UserInfoDTO;
import com.ecommerce.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    RegistrationDTO registerUser(RegistrationDTO userRegisterRequest);

    JwtResponseDto singIn(LoginRequestDto loginRequestDto);

    List<UserInfoDTO> getAllUsers();
}
