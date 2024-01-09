package ch.bbzsogr.todo.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDao {
    @NotNull(message = "email must be sent")
    @NotBlank(message = "email cannot be blank")
    @Email(message = "email must be valid")
    private String email;

    @NotNull(message = "password must be sent")
    @NotBlank(message = "password cannot be blank")
    private String password;
}
