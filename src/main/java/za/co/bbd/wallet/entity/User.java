package za.co.bbd.wallet.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private String userId;

    private String firstName;

    private String surname;

    private String email;

    private String phoneNumber;

    @OneToMany
    private List<Account> accounts = new ArrayList<>();

    private String password;
}
