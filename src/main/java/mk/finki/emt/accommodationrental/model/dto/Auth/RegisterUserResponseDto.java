package mk.finki.emt.accommodationrental.model.dto.Auth;


import mk.finki.emt.accommodationrental.model.domain.User;
import mk.finki.emt.accommodationrental.model.enums.Role;

public record RegisterUserResponseDto(
    String username,
    String name,
    String surname,
    String email,
    Role role
) {
    public static RegisterUserResponseDto from(User user) {
        return new RegisterUserResponseDto(
            user.getUsername(),
            user.getName(),
            user.getSurname(),
            user.getEmail(),
            user.getRole()
        );
    }
}


