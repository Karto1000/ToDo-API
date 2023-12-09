package ch.bbzsogr.todo.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Getter
@Setter
public class RegisterRequestDao {
    @NonNull
    @NotBlank
    private String firstname;

    @NonNull
    @NotBlank
    private String lastname;

    @NonNull
    @NotBlank
    @Email
    private String email;

    @NonNull
    @NotBlank
    private String password;
}
