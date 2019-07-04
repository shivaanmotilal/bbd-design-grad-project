package za.co.bbd.wallet.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customerId",
        "firstName",
        "surname",
        "email",
        "phoneNumber",
        "password",
        "accountEntities"})
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerDto implements Serializable {

    @ApiModelProperty(name = "customer-id", example = "5000328",
            notes = "The unique number used to identify the wallet customer")
    @JsonProperty(value = "customer-id")
    @NotNull
    @Size(min = 7, max = 7)
    private String customerId;

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

    @ApiModelProperty(name = "accountEntities",
            notes = "The list of accountEntities associated with the user")
    @JsonProperty(value = "accountEntities")
    @NotNull
    private List<AccountDto> accountDtos;

    @ApiModelProperty(name = "password",
            notes = "Password associated with the user")
    @JsonProperty(value = "password")
    @NotNull
    private String password;
}
