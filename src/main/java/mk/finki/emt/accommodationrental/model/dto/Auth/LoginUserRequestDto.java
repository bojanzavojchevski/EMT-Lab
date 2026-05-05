package mk.finki.emt.accommodationrental.model.dto.Auth;

public record LoginUserRequestDto(
    String username,
    String password
) {
}
