package za.co.bbd.wallet.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name="Payment")
public class PaymentEntity {

    @Id
    @Column(name="PaymentId")
    private String PaymentId;

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
    private int settled;
}
