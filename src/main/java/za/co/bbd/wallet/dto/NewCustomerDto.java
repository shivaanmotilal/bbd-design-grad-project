package za.co.bbd.wallet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@NoArgsConstructor
//@AllArgsConstructor
@Data
//@Builder
public class NewCustomerDto implements Serializable {

    @ApiModelProperty(name = "first-name", example = "Shai",
            notes = "The first name of the user")
    @JsonProperty(value = "first-name")
    @NotNull
    private String firstName;

    @ApiModelProperty(name = "surname", example = "Labeouf",
            notes = "The surname of the user")
    @JsonProperty(value = "surname")
    @NotNull
    private String surname;

    @ApiModelProperty(name = "email", example = "hotmale@hotmail.com",
            notes = "The user email address")
    @JsonProperty(value = "email")
    @NotNull
    @Pattern(message = "invalid email address", regexp = "^(.*@.*\\..*)")
    private String email;

    @ApiModelProperty(name = "phone-number", example = "0711234567",
            notes = "The user phone number")
    @JsonProperty(value = "phone-number")
    @NotNull
    private String phoneNumber;

    @ApiModelProperty(name = "password",
            notes = "Password associated with the user")
    @JsonProperty(value = "password")
    @NotNull
    private String password;

    @ApiModelProperty(name = "confirmed-password",
            notes = "Password associated with the user")
    @JsonProperty(value = "confirmed-password")
    @NotNull
    private String confirmedPassword;
}
