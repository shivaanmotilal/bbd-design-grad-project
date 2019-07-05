package za.co.bbd.wallet.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "Customer")
public class CustomerEntity {

    @Id
//    @GeneratedValue
    private String customerId;

    private String firstName;

    private String surname;

    private String email;

    private String phoneNumber;

    @OneToMany
    private List<AccountEntity> accounts = new ArrayList<>();

    private String password;
}
