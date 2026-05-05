package mk.finki.emt.accommodationrental.service.application;



import mk.finki.emt.accommodationrental.model.dto.Auth.LoginUserRequestDto;
import mk.finki.emt.accommodationrental.model.dto.Auth.LoginUserResponseDto;
import mk.finki.emt.accommodationrental.model.dto.Auth.RegisterUserRequestDto;
import mk.finki.emt.accommodationrental.model.dto.Auth.RegisterUserResponseDto;

import java.util.Optional;

public interface UserApplicationService {
    Optional<RegisterUserResponseDto> register(RegisterUserRequestDto registerUserRequestDto);

    Optional<LoginUserResponseDto> login(LoginUserRequestDto loginUserRequestDto);

    Optional<RegisterUserResponseDto> findByUsername(String username);
}
