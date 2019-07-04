package za.co.bbd.wallet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Account {

    @Id
    private String accountNumber;

    private double balance;

    private double availableBalance;

    private boolean closed;

    @OneToMany
    private List<Transactions> transactions = new ArrayList<>();
}

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
class Transactions {
    @Id
    private String transactionId;
}