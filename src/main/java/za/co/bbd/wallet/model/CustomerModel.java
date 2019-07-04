package za.co.bbd.wallet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerModel {

    @NotNull
    private String customerId;

    @NotNull
    private String firstName;

    @NotNull
    private String surname;

    @NotNull
    private String email;

    @NotNull
    private String phoneNumber;

    @NotNull
    private List<AccountModel> accounts;

    @NotNull
    private String password;

    public void addAccount(AccountModel account) {
        if (this.accounts == null) {
            this.accounts = new ArrayList<>();
        }
        this.accounts.add(account);
    }
}
