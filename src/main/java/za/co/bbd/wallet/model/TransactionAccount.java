package za.co.bbd.wallet.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "accountNumber",
        "openingBalance"})
@Getter
@Setter
public class TransactionAccount {
    @ApiModelProperty(name = "account-number", required = true, example = "10001284657",
            notes = "The unique account number specified for any given account")
    @JsonProperty(value = "account-number", required = true)
    @NotNull
    @Size(min = 11, max = 11)
    @Pattern(message = "invalid account number", regexp = "^(1[0]{5}[0-9]{6})$")
    private String accountNumber;

    @ApiModelProperty(name = "balance", required = true, example = "30000.00",
            notes = "The balance for the account")
    @JsonProperty(value = "balance", required = true)
    @NotNull
    private double openingBalance;
}
