package za.co.bbd.wallet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import za.co.bbd.wallet.enums.TransactionType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "transactionId",
        "primaryAccountHolderId",
        "primaryAccountNumber",
        "balance",
        "amount",
        "secondaryAccountHolder",
        "secondaryAccountNumber",
        "dateInitiation",
        "dateSettlement",
        "reference",
        "settled",
        "authorized"})
@Getter
@Setter
@Entity
public class Transaction {

    @ApiModelProperty(name = "transaction-id", required = true, example = "3b385ef0-d76c-4f0f-add8-b4ecf41874d6",
            notes = "The unique id of the transaction")
    @JsonProperty(value = "transaction-id", required = true)
    @NotNull
    @Id
    @GeneratedValue
    private UUID transactionId;

    @ApiModelProperty(name = "transaction-type", required = true, example = "TRANSFER",
            notes = "The category of transaction")
    @JsonProperty(value = "transaction-type", required = true)
    @NotNull
    private TransactionType transactionType;

    @ApiModelProperty(name = "primary-account-holder-id", required = true, example = "5000654",
            notes = "The account holder ID")
    @Pattern(message = "invalid account holder id", regexp = "^(5[0]{3}[0-9]{3})")
    @JsonProperty(value = "primary-account-holder-id", required = true)
    @NotNull
    private String primaryAccountHolderId;

    @ApiModelProperty(name = "primary-account-number", required = true, example = "10001288757",
            notes = "The unique id of the transaction")
    @JsonProperty(value = "primary-account-number", required = true)
    @NotNull
    private String primaryAccountNumber;

    @ApiModelProperty(name = "balance", required = true, example = "30000.00",
            notes = "The available balance remaining in the account")
    @JsonProperty(value = "balance", required = true)
    @NotNull
    private double balance;

    @ApiModelProperty(name = "amount", required = true, example = "500.00",
            notes = "The transaction amount")
    @JsonProperty(value = "amount", required = true)
    @NotNull
    private double amount;

    @ApiModelProperty(name = "secondary-account-holder", required = true, example = "John",
            notes = "The account holder name of the alternative party of the transaction")
    @JsonProperty(value = "secondary-account-holder", required = true)
    private String secondaryAccountHolder;

    @ApiModelProperty(name = "secondary-account-number", required = true, example = "10001284657",
            notes = "The account number of the alternative party of the transaction")
    @JsonProperty(value = "secondary-account-number", required = true)
    @Pattern(message = "invalid account number", regexp = "^(1[0]{3}[0-9]{7})")
    private String secondaryAccountNumber;

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

    @ApiModelProperty(name = "reference", required = true, example = "TO THE VICTOR GO THE SPOILS",
            notes = "A brief description of the transaction")
    @JsonProperty(value = "reference", required = true)
    @Size(max = 30)
    private String reference;

    @ApiModelProperty(name = "settled", required = true, example = "true",
            notes = "Whether or not the transaction has been settled")
    @JsonProperty(value = "settled", required = true)
    @NotNull
    private boolean settled;

    @ApiModelProperty(name = "authorized", required = true, example = "false",
            notes = "Whether or not the transaction has been authorized")
    @JsonProperty(value = "authorized", required = true)
    @NotNull
    private boolean authorized;

}
