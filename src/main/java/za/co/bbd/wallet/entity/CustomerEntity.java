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
@Table(name="Customer")
public class CustomerEntity {

    @Id
//    @GeneratedValue
    @Column(name="customerId")
    private String customerId;

    @Column(name="firstName")
    private String firstName;

    @Column(name="surname")
    private String surname;

    @Column(name="email")
    private String email;

    @Column(name="phoneNumber")
    private String phoneNumber;

    @OneToMany
    private List<AccountEntity> accounts = new ArrayList<>();

    @Column(name="`password`")
    private String password;
}
