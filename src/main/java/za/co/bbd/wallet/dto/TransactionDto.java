package za.co.bbd.wallet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "transactionId",
        "fromAccount",
        "toAccount",
        "amount",
        "dateInitiation",
        "dateSettlement",
        "settled"})
@AllArgsConstructor
@Data
@Builder
public class TransactionDto {

    @ApiModelProperty(name = "transaction-id", required = true, example = "3b385ef0-d76c-4f0f-add8-b4ecf41874d6",
            notes = "The unique id of the transaction")
    @JsonProperty(value = "transaction-id", required = true)
    @NotNull
    private UUID transactionId;

    @ApiModelProperty(name = "from-account-number", required = true,
            notes = "The account funds are coming from")
    @JsonProperty(value = "from-account-number", required = true)
    @NotNull
    private String fromAccountNumber;

    @ApiModelProperty(name = "from-account-opening-balance", required = true,
            notes = "The account funds are coming from")
    @JsonProperty(value = "from-account-opening-balance", required = true)
    @NotNull
    private String fromAccountOpeningBalance;

    @ApiModelProperty(name = "to-account-number", required = true,
            notes = "The account funds are going to")
    @JsonProperty(value = "to-account-number", required = true)
    @NotNull
    private String toAccountNumber;

    @ApiModelProperty(name = "to-account-opening-balance", required = true,
            notes = "The account funds are going to")
    @JsonProperty(value = "to-account-opening-balance", required = true)
    @NotNull
    private String toAccountOpeningBalance;

    @ApiModelProperty(name = "amount", required = true, example = "500.00",
            notes = "The transaction amount")
    @JsonProperty(value = "amount", required = true)
    @NotNull
    private double amount;

    @ApiModelProperty(name = "date-initiation", required = true, example = "2019-05-30",
            notes = "The date on which the transaction was initiated")
    @JsonProperty(value = "date-initiation", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDate dateInitiation;

    @ApiModelProperty(name = "date-settlement", required = true, example = "2019-05-31",
            notes = "The date on which the transaction was settled")
    @JsonProperty(value = "date-settlement", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDate dateSettlement;

    @ApiModelProperty(name = "settled", required = true, example = "true",
            notes = "Whether or not the transaction has been settled")
    @JsonProperty(value = "settled", required = true)
    @NotNull
    private boolean settled;

    public TransactionDto() {
        this.transactionId = UUID.randomUUID();
    }

    public TransactionDto(
            @NotNull String fromAccountNumber,
            @NotNull String fromAccountOpeningBalance,
            @NotNull String toAccountNumber,
            @NotNull String toAccountOpeningBalance,
            @NotNull double amount,
            LocalDate dateInitiation,
            LocalDate dateSettlement) {
        this.transactionId = UUID.randomUUID();
        this.fromAccountNumber = fromAccountNumber;
        this.fromAccountOpeningBalance = fromAccountOpeningBalance;
        this.toAccountNumber = toAccountNumber;
        this.toAccountOpeningBalance = toAccountOpeningBalance;
        this.amount = amount;
        this.dateInitiation = dateInitiation;
        this.dateSettlement = dateSettlement;
        this.settled = false;
    }
}
