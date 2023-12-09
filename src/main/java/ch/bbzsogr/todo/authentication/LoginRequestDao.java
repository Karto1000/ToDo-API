package ch.bbzsogr.todo.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDao {
    @NonNull
    @NotBlank
    @Email
    private String email;

    @NonNull
    @NotBlank
    private String password;
}
