package mk.finki.emt.accommodationrental.service.application.impl;


import mk.finki.emt.accommodationrental.helpers.JwtHelper;
import mk.finki.emt.accommodationrental.model.domain.User;
import mk.finki.emt.accommodationrental.model.dto.Auth.LoginUserRequestDto;
import mk.finki.emt.accommodationrental.model.dto.Auth.LoginUserResponseDto;
import mk.finki.emt.accommodationrental.model.dto.Auth.RegisterUserRequestDto;
import mk.finki.emt.accommodationrental.model.dto.Auth.RegisterUserResponseDto;
import mk.finki.emt.accommodationrental.service.application.UserApplicationService;
import mk.finki.emt.accommodationrental.service.domain.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserApplicationServiceImpl implements UserApplicationService {
    private final UserService userService;
    private final JwtHelper jwtHelper;

    public UserApplicationServiceImpl(UserService userService, JwtHelper jwtHelper) {
        this.userService = userService;
        this.jwtHelper = jwtHelper;
    }

    @Override
    public Optional<RegisterUserResponseDto> register(RegisterUserRequestDto registerUserRequestDto) {
        User user = userService.register(registerUserRequestDto.toUser());
        RegisterUserResponseDto displayUserDto = RegisterUserResponseDto.from(user);
        return Optional.of(displayUserDto);
    }

    @Override
    public Optional<LoginUserResponseDto> login(LoginUserRequestDto loginUserRequestDto) {
        User user = userService.login(loginUserRequestDto.username(), loginUserRequestDto.password());

        String token = jwtHelper.generateToken(user);

        return Optional.of(new LoginUserResponseDto(token));
    }

    @Override
    public Optional<RegisterUserResponseDto> findByUsername(String username) {
        return userService
            .findByUsername(username)
            .map(RegisterUserResponseDto::from);
    }
}

