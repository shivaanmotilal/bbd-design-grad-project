package za.co.bbd.wallet.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "userId",
        "firstName",
        "surname",
        "email",
        "phoneNumber",
        "accounts"})
@Getter
@Setter
@Entity
public class User {

    @ApiModelProperty(name = "user-id", required = true, example = "5000328",
            notes = "The unique number used to identify the wallet user")
    @JsonProperty(value = "user-id", required = true)
    @NotNull
    @Size(min = 7, max = 7)
    @Pattern(message = "invalid user id", regexp = "^(5[0]{3}[0-9]{3})")
    @Id
    @GeneratedValue
    private String userId;

    @ApiModelProperty(name = "first-name", required = true, example = "Shai",
            notes = "The first name of the user")
    @JsonProperty(value = "first-name", required = true)
    @NotNull
    private String firstName;

    @ApiModelProperty(name = "surname", required = true, example = "Labeouf",
            notes = "The surname of the user")
    @JsonProperty(value = "surname", required = true)
    @NotNull
    private String surname;

    @ApiModelProperty(name = "minimum-deposit", required = true, example = "hotmale@hotmail.com",
            notes = "The user email address")
    @JsonProperty(value = "minimum-deposit", required = true)
    @NotNull
    @Pattern(message = "invalid email address", regexp = "^(.*@.*\\..*)")
    private String email;

    @ApiModelProperty(name = "phone-number", required = true, example = "0711234567",
            notes = "The user phone number")
    @JsonProperty(value = "phone-number", required = true)
    @NotNull
    private String phoneNumber;

    @ApiModelProperty(name = "accounts", required = true,
            notes = "The list of accounts associated with the user")
    @JsonProperty(value = "accounts", required = true)
    @NotNull
    @OneToMany
    private List<Account> accounts;

    public void User() { }

    public void User(String userId, String firstName, String surname, String email, String phoneNumber, List<Account> accounts) {
        this.userId = userId;
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.accounts = accounts;
    }

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void addAccounts(Account account) {
        if (this.accounts == null) {
            this.accounts = new ArrayList<>();
        }
        this.accounts.add(account);
    }
}
