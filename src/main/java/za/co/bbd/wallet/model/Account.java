package za.co.bbd.wallet.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "accountNumber",
        "accountName",
        "balance",
        "minimumDeposit",
        "overdraftLimit",
        "transactions"})
@Getter
@Setter
@Entity
public class Account {

    @ApiModelProperty(name = "account-number", required = true, example = "10001284657",
            notes = "The unique account number specified for any given account")
    @JsonProperty(value = "account-number", required = true)
    @NotNull
    @Size(min = 11, max = 11)
    @Pattern(message = "invalid account number", regexp = "^(1[0]{5}[0-9]{6})$")
    @Id
    private String accountNumber;

    @ApiModelProperty(name = "account-name", required = true, example = "Savings for the Apocalypse",
            notes = "The personalised name of the account chosen for by the client")
    @JsonProperty(value = "account-name", required = true)
    @NotNull
    private String accountName;

    @ApiModelProperty(name = "balance", required = true, example = "30000.00",
            notes = "The available balance for the account")
    @JsonProperty(value = "balance", required = true)
    @NotNull
    private double balance;

    @ApiModelProperty(name = "minimum-deposit", required = true, example = "1000.00",
            notes = "The minimum deposit necessary for the account")
    @JsonProperty(value = "minimum-deposit", required = true)
    @NotNull
    private double minimumDeposit;

    @ApiModelProperty(name = "overdraft-limit", required = true, example = "30000.00",
            notes = "The maximum available overdraft for the account")
    @JsonProperty(value = "overdraft-limit", required = true)
    @NotNull
    private double overdraftLimit;

    @ApiModelProperty(name = "transactions", required = true,
            notes = "A history of recent transactions for the account")
    @JsonProperty(value = "transactions", required = true)
    @NotNull
    @OneToMany
    private List<Transaction> transactions;

    /**
     * Constructs a new <code>Account</code>.
     */
    public Account() {}

    /**
     * Constructs a new <code>Account</code>.
     *
     * @param accountNumber     the unique account number
     * @param accountName       the personal name of the account
     * @param balance           the current balance of the account
     * @param minimumDeposit    the minimum deposit required to open the account
     * @param overdraftLimit    the overdraft limit for this particular account
     * @param transactions      the recent transactions of the account
     */
    public Account(String accountNumber, String accountName, double balance, double minimumDeposit, double overdraftLimit, List<Transaction> transactions) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balance = balance;
        this.minimumDeposit = minimumDeposit;
        this.overdraftLimit = overdraftLimit;
        this.transactions = transactions;
    }

    /**
     * Retrieve the account number.
     *
     * @return the account number
     */
    public String getAccountNumber() {
        return this.accountNumber;
    }

    /**
     * Retrieve the account name.
     *
     * @return the account name
     */
    public String getAccountName() { return this.accountName; }

    /**
     * Retrieve the account balance.
     *
     * @return the account balance
     */
    public double getAccountBalance() { return this.balance; }

    /**
     * Retrieve the minimum deposit
     *
     * @return the minimum deposit
     */
    public double getMinimumDeposit() { return this.minimumDeposit; }

    /**
     * Retrieve the overdraft limit.
     *
     * @return the overdraft limit
     */
    public double getOverdraftLimit() { return this.overdraftLimit; }

    /**
     * Retrieve the transactions.
     *
     * @return the transactions
     */
    public List<Transaction> getTransactions() { return this.transactions; }

    /**
     * Set the account number.
     *
     * @param accountNumber     the account number
     */
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    /**
     * Set the account name.
     *
     * @param accountName     the account name
     */
    public void setAccountName(String accountName) { this.accountName = accountName; }

    /**
     * Set the account balance.
     *
     * @param balance     the account balance
     */
    public void setAccountBalance(double balance) { this.balance = balance; }

    /**
     * Set the minimum deposit.
     *
     * @param minimumDeposit     the minimum deposit
     */
    public void setMinimumDeposit(double minimumDeposit) { this.minimumDeposit = minimumDeposit; }

    /**
     * Set the overdraft limit.
     *
     * @param overdraftLimit    the overdraft limit
     */
    public void setOverdraftLimit(double overdraftLimit) { this.overdraftLimit = overdraftLimit; }

    /**
     * Set the transaction list.
     *
     * @param transactions      the transaction list
     */
    public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }

    /**
     * Add a new transaction to the account.
     *
     * @param transaction       the property
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }


}
