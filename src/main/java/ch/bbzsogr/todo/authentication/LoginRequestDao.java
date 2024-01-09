package ch.bbzsogr.todo.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDao {
    @NotNull(message = "Email must be sent")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Password must be sent")
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
