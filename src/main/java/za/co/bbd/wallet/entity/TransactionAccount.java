package za.co.bbd.wallet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class TransactionAccount {
    @Id
    @GeneratedValue
    private String Id;

    private String accountNumber;

    private double openingBalance;
}
