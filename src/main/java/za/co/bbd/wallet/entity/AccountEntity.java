package za.co.bbd.wallet.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "Account")
public class AccountEntity {

    @Id
    private String accountNumber;

    private double balance;

    private double availableBalance;

    private boolean closed;

    @ManyToMany
    private List<TransactionEntity> transactions = new ArrayList<>();
}