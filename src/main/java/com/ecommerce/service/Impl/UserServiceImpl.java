package com.ecommerce.service.Impl;

import com.ecommerce.dto.LoginRequestDto;
import com.ecommerce.dto.RegistrationDTO;
import com.ecommerce.dto.response.JwtResponseDto;
import com.ecommerce.dto.response.UserInfoDTO;

import com.ecommerce.exception.CustomException;
import com.ecommerce.exception.UserAlreadyExistsException;
import com.ecommerce.exception.UserNotFoundException;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.UserService;
import com.ecommerce.service.jwt.JwtTokenProvider;
import com.entity.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;


    private final JwtTokenProvider jwtTokenProvider;


    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authenticationManager;

    @Override
    public JwtResponseDto  singIn(LoginRequestDto loginRequestDto) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginRequestDto.getEmail(),
//                        loginRequestDto.getPassword()
//                )
//        );
        UserEntity user = userRepository.getUserByEmail(loginRequestDto.getEmail()).orElseThrow(() -> new UserNotFoundException("User Not Found For This Email"));
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new CustomException("Password Not Correct", HttpStatus.BAD_REQUEST);
        }
//        LoginRequestDto loginRequest = new LoginRequestDto();
//        loginRequest.setEmail(user.getEmail());
//        loginRequest.setPassword(user.getPassword());
        return getTokenResponse(user);
    }

    private JwtResponseDto getTokenResponse(UserEntity user) {
            return jwtTokenProvider.createAccessToken(user.getEmail());
    }

    @Override
    public List<UserInfoDTO> getAllUsers() {
        List<UserEntity> userEntity=userRepository.findAll();
//        CONVERT To DTO
        return userEntity.stream().map(this::mapToUserInfoDTO).collect(Collectors.toList());
//        UserInfoDTO userInfoDTO=new UserInfoDTO();
//        return null;
    }


    @Override
    public RegistrationDTO registerUser(RegistrationDTO userRegisterRequest) {
        Optional<UserEntity> userByUsername = userRepository.getUserByEmail(userRegisterRequest.getEmail());

        if (userByUsername.isPresent()) {
            System.out.println("userByUsername.isPresent() = " + userByUsername.isPresent());
            throw new UserAlreadyExistsException("User already  exists");
        }
        UserEntity user = new UserEntity();
        user.setEmail(userRegisterRequest.getEmail());
        user.setFirstName(userRegisterRequest.getFirstName());
        user.setLastName(userRegisterRequest.getLastName());
        user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        UserEntity savedUser = userRepository.save(user);

        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setId(savedUser.getId());
        registrationDTO.setFirstName(savedUser.getFirstName());
        registrationDTO.setLastName(savedUser.getLastName());
        registrationDTO.setEmail(savedUser.getEmail());
        return registrationDTO;
    }

    public UserInfoDTO mapToUserInfoDTO(UserEntity userEntity){
        UserInfoDTO infoDTO=new UserInfoDTO();
        infoDTO.setId(userEntity.getId());
        infoDTO.setFirstName(userEntity.getFirstName());
        infoDTO.setLastName(userEntity.getLastName());
        infoDTO.setEmail(userEntity.getEmail());
        return infoDTO;
    }
}
