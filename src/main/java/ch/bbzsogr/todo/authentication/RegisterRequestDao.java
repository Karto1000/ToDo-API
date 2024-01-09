package ch.bbzsogr.todo.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Getter
@Setter
public class RegisterRequestDao {
    @NotNull(message = "firstname cannot be empty")
    @NotBlank(message = "firstname cannot be blank")
    private String firstname;

    @NotNull(message = "lastname cannot be empty")
    @NotBlank(message = "lastname cannot be blank")
    private String lastname;

    @NotNull(message = "email cannot be empty")
    @NotBlank(message = "email cannot be blank")
    @Email
    private String email;

    @NotNull(message = "password name cannot be empty")
    @NotBlank(message = "password cannot be blank")
    private String password;

    @NotNull(message = "confirmation password cannot be empty")
    @NotBlank(message = "confirmation password cannot be blank")
    private String confirmationPassword;
}
