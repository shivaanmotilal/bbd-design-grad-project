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
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "accountNumber",
        "balance",
        "availableBalance",
        "closed",
        "transactions"})
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AccountDto {

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
    private double balance;

    @ApiModelProperty(name = "available-balance", required = true, example = "900.00",
            notes = "The account available balance")
    @JsonProperty(value = "available-balance", required = true)
    @NotNull
    private double availableBalance;

    @ApiModelProperty(name = "closed", required = true, example = "false",
            notes = "Whether or not the account has been closed")
    @JsonProperty(value = "closed", required = true)
    @NotNull
    private boolean closed;

    @ApiModelProperty(name = "transactions", required = true,
            notes = "A list of transactionEntities associated with the account")
    @JsonProperty(value = "transactions", required = true)
    @NotNull
    private List<UUID> transactions;
}
