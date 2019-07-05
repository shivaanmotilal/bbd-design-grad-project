package za.co.bbd.wallet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@Data
public class AuthorizationDto implements Serializable {

    @ApiModelProperty(name = "from-account-number", example = "100009275",
            notes = "The account from which funds will be withdrawn")
    @JsonProperty(value = "from-account-number")
    @NotNull
    private String fromAccountNumber;

    @ApiModelProperty(name = "to-account-number", example = "100006014",
            notes = "The account into which funds will be transferred")
    @JsonProperty(value = "to-account-number")
    @NotNull
    private String toAccountNumber;

    @ApiModelProperty(name = "amount",
            notes = "The transaction amount")
    @JsonProperty(value = "amount")
    private double amount;

}
