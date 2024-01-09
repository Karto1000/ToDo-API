package ch.bbzsogr.todo.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDao {
    @NotNull(message = "Firstname must be sent")
    @NotBlank(message = "Firstname cannot be blank")
    private String firstname;

    @NotNull(message = "Lastname must be sent")
    @NotBlank(message = "Lastname cannot be blank")
    private String lastname;

    @NotNull(message = "Email cannot be empty")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Password must be sent")
    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotNull(message = "Repeated Password must be sent")
    @NotBlank(message = "Repeated Password cannot be blank")
    private String repeatedPassword;
}
