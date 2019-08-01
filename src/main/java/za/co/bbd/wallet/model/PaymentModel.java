package za.co.bbd.wallet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class PaymentModel {

    @NotNull
    private UUID PaymentId;

    @NotNull
    private String fromAccountNumber;

    @NotNull
    private String fromAccountOpeningBalance;

    @NotNull
    private String toAccountNumber;

    @NotNull
    private String toAccountOpeningBalance;

    @NotNull
    private double amount;

    private LocalDate dateInitiation;

    private LocalDate dateSettlement;

    @NotNull
    private int settled;

    public PaymentModel() {
        this.PaymentId = UUID.randomUUID();
    }

    public PaymentModel(
            @NotNull String fromAccountNumber,
            @NotNull String fromAccountOpeningBalance,
            @NotNull String toAccountNumber,
            @NotNull String toAccountOpeningBalance,
            @NotNull double amount,
            LocalDate dateInitiation,
            LocalDate dateSettlement) {
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
