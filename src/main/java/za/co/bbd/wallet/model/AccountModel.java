package za.co.bbd.wallet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AccountModel {

    @NotNull
    private String accountNumber;

    @NotNull
    private double balance;

    @NotNull
    private double availableBalance;

    @NotNull
    private int closed;

    private List<UUID> Payments;

    public void addPayment(UUID PaymentId) {
        if (Payments == null) {
            Payments = new ArrayList<>();
        }
        Payments.add(PaymentId);
    }
}
