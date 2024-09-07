package com.ecommerce.service;

import com.ecommerce.dto.LoginRequestDto;
import com.ecommerce.dto.RegistrationDTO;
import com.ecommerce.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    RegistrationDTO registerUser(RegistrationDTO userRegisterRequest);

    UserEntity loginUser(LoginRequestDto loginRequestDto);
}
