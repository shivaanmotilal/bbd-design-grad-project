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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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
@Getter
@Setter
public class Transaction {

    @ApiModelProperty(name = "transaction-id", required = true, example = "3b385ef0-d76c-4f0f-add8-b4ecf41874d6",
            notes = "The unique id of the transaction")
    @JsonProperty(value = "transaction-id", required = true)
    @NotNull
    private UUID transactionId;

    @ApiModelProperty(name = "from-account", required = true,
            notes = "The account funds are coming from")
    @JsonProperty(value = "from-account", required = true)
    @NotNull
    private TransactionAccount fromAccount;

    @ApiModelProperty(name = "to-account", required = true,
            notes = "The account funds are going to")
    @JsonProperty(value = "to-account", required = true)
    @NotNull
    private TransactionAccount toAccount;

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

    public Transaction() {
        this.transactionId = UUID.randomUUID();
    }

    public Transaction(
            @NotNull TransactionAccount fromAccount,
            @NotNull TransactionAccount toAccount,
            @NotNull double amount,
            LocalDate dateInitiation,
            LocalDate dateSettlement) {
        this.transactionId = UUID.randomUUID();;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.dateInitiation = dateInitiation;
        this.dateSettlement = dateSettlement;
        this.settled = false;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public TransactionAccount getFromAccount() {
        return fromAccount;
    }

    public TransactionAccount getToAccount() {
        return toAccount;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDateInitiation() {
        return dateInitiation;
    }

    public LocalDate getDateSettlement() {
        return dateSettlement;
    }

    public boolean isSettled() {
        return settled;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public void setFromAccount(TransactionAccount fromAccount) {
        this.fromAccount = fromAccount;
    }

    public void setToAccount(TransactionAccount toAccount) {
        this.toAccount = toAccount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDateInitiation(LocalDate dateInitiation) {
        this.dateInitiation = dateInitiation;
    }

    public void setDateSettlement(LocalDate dateSettlement) {
        this.dateSettlement = dateSettlement;
    }

    public void setSettled(boolean settled) {
        this.settled = settled;
    }
}
