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
@Entity
@Table(name="Account")
public class AccountEntity {

    @Id
    @Column(name="accountNumber")
    private String accountNumber;

    @Column(name="accountBalance")
    private double accountBalance;

    @Column(name="availableBalance")
    private double availableBalance;

    @Column(name="closedAccount")
    private int closedAccount;

    @ManyToMany
    private List<PaymentEntity> Payments = new ArrayList<>();
}