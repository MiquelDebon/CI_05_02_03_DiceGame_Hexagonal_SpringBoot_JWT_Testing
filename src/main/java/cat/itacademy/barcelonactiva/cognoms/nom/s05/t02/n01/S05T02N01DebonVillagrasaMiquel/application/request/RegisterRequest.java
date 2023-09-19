package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Size(min = 2, max = 20, message = "Name: must be between 2 and 20 characters")
    private String firstname;

    @Size(min = 2, max = 20, message = "Surname: must be between 2 and 20 characters")
    private String lastname;

    @Pattern(   regexp = "^(.+)@(\\S+)$",
                message = "Email: must be a valid email address")
    private String email;

    @Size(min = 8, max = 100)
    @Pattern(   regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()]).{8,}$",
                message = "Password: Minimum eight characters, at least one lower and upper case letter, one number, and one special character")
    private String password;
}
