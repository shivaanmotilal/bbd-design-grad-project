package za.co.bbd.wallet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "PaymentId",
        "fromAccountNumber",
        "fromAccountOpeningBalance",
        "toAccountNumber",
        "toAccountOpeningBalance",
        "amount",
        "dateInitiation",
        "dateSettlement",
        "settled"})
@AllArgsConstructor
@Data
@Builder
@Component("wallet.PaymentDto")
public class PaymentDto implements Serializable {

    @ApiModelProperty(name = "Payment-id", example = "3b385ef0-d76c-4f0f-add8-b4ecf41874d6",
            notes = "The unique id of the Payment")
    @JsonProperty(value = "Payment-id")
    @NotNull
    private UUID PaymentId;

    @ApiModelProperty(name = "from-account-number",
            notes = "The account funds are coming from")
    @JsonProperty(value = "from-account-number")
    @NotNull
    private String fromAccountNumber;

    @ApiModelProperty(name = "from-account-opening-balance",
            notes = "The account funds are coming from")
    @JsonProperty(value = "from-account-opening-balance")
    private double fromAccountOpeningBalance;

    @ApiModelProperty(name = "to-account-number",
            notes = "The account funds are going to")
    @JsonProperty(value = "to-account-number")
    @NotNull
    private String toAccountNumber;

    @ApiModelProperty(name = "to-account-opening-balance",
            notes = "The account funds are going to")
    @JsonProperty(value = "to-account-opening-balance")
    private double toAccountOpeningBalance;

    @ApiModelProperty(name = "amount", example = "500.00",
            notes = "The Payment amount")
    @JsonProperty(value = "amount")
    private double amount;

    @ApiModelProperty(name = "date-initiation", example = "2019-05-30",
            notes = "The date on which the Payment was initiated")
    @JsonProperty(value = "date-initiation")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private String dateInitiation;

    @ApiModelProperty(name = "date-settlement", example = "2019-05-31",
            notes = "The date on which the Payment was settled")
    @JsonProperty(value = "date-settlement")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private String dateSettlement;

    @ApiModelProperty(name = "settled", example = "true",
            notes = "Whether or not the Payment has been settled")
    @JsonProperty(value = "settled")
    private int settled;

    public PaymentDto() {
        this.PaymentId = UUID.randomUUID();
    }

    public PaymentDto(
            String fromAccountNumber,
            double fromAccountOpeningBalance,
            String toAccountNumber,
            double toAccountOpeningBalance,
            double amount,
            String dateInitiation,
            String dateSettlement) {
        this.PaymentId = UUID.randomUUID();
        this.fromAccountNumber = fromAccountNumber;
        this.fromAccountOpeningBalance = fromAccountOpeningBalance;
        this.toAccountNumber = toAccountNumber;
        this.toAccountOpeningBalance = toAccountOpeningBalance;
        this.amount = amount;
        this.dateInitiation = dateInitiation;
        this.dateSettlement = dateSettlement;
        this.settled = 0;
    }
}
