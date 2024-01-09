package ch.bbzsogr.todo.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * The DAO that is received in the register request
 */
@Getter
@Setter
public class RegisterRequestDao {
    @NotNull(message = "firstname must be sent")
    @NotBlank(message = "firstname cannot be blank")
    private String firstname;

    @NotNull(message = "lastname must be sent")
    @NotBlank(message = "lastname cannot be blank")
    private String lastname;

    @NotNull(message = "email cannot be empty")
    @NotBlank(message = "email cannot be blank")
    @Email(message = "email must be valid")
    private String email;

    @NotNull(message = "password must be sent")
    @NotBlank(message = "password cannot be blank")
    private String password;

    @NotNull(message = "repeatedPassword must be sent")
    @NotBlank(message = "repeatedPassword cannot be blank")
    private String repeatedPassword;
}
