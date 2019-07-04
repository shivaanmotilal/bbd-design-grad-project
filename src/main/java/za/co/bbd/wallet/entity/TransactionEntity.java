package za.co.bbd.wallet.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "Transaction")
public class TransactionEntity {

    @Id
    @GeneratedValue
    private String transactionId;

    private String fromAccountId;
    private double fromOpeningBalance;

    private String toAccountId;
    private double toOpeningBalance;

    private double amount;

    private Date dateInitiation;
    private Date dateSettlement;

    private boolean settled;
}
