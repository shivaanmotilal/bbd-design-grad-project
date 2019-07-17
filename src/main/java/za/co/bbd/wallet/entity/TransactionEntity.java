package za.co.bbd.wallet.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name="Transaction")
public class TransactionEntity {

    @Id
    @Column(name="transactionId")
    private String transactionId;

    @Column(name="fromAccountNumber")
    private String fromAccountNumber;

    @Column(name="fromAccountOpeningBalance")
    private double fromAccountOpeningBalance;

    @Column(name="toAccountNumber")
    private String toAccountNumber;

    @Column(name="toAccountOpeningBalance")
    private double toAccountOpeningBalance;

    @Column(name="amount")
    private double amount;

    @Column(name="dateInitiation")
    private Date dateInitiation;

    @Column(name="dateSettlement")
    private Date dateSettlement;

    @Column(name="settled")
    private boolean settled;
}
