package za.co.bbd.wallet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private String transactionId;

    private String fromAccountId;

    private String toAccountId;

    private double amount;

    private LocalDate dateInitiation;

    private LocalDate dateSettlement;

    private boolean settled;
}
