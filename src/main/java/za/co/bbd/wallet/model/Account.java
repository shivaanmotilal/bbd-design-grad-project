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
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "accountNumber",
        "balance",
        "availableBalance",
        "closed",
        "transactions"})
@Getter
@Setter
public class Account {

    @ApiModelProperty(name = "account-number", required = true, example = "10001284657",
            notes = "The unique account number specified for any given account")
    @JsonProperty(value = "account-number", required = true)
    @NotNull
    @Size(min = 11, max = 11)
    @Pattern(message = "invalid account number", regexp = "^(1[0]{5}[0-9]{6})$")
    private String accountNumber;

    @ApiModelProperty(name = "balance", required = true, example = "30000.00",
            notes = "The balance for the account")
    @JsonProperty(value = "balance", required = true)
    @NotNull
    private double balance;

    @ApiModelProperty(name = "available-balance", required = true, example = "900.00",
            notes = "The account available balance")
    @JsonProperty(value = "available-balance", required = true)
    @NotNull
    private double availableBalance;

    @ApiModelProperty(name = "closed", required = true, example = "false",
            notes = "Whether or not the account has been closed")
    @JsonProperty(value = "closed", required = true)
    @NotNull
    private boolean closed;

    @ApiModelProperty(name = "transactions", required = true,
            notes = "A list of transactions associated with the account")
    @JsonProperty(value = "transactions", required = true)
    @NotNull
    private List<UUID> transactions;

    /**
     * Constructs a new <code>Account</code>.
     */
    public Account() {}

    /**
     * Constructs a new <code>Account</code>.
     *
     * @param accountNumber     the unique account number
     * @param balance           the current balance of the account
     * @param availableBalance  the available amount in the account
     * @param transactions      the recent transactions of the account
     */
    public Account(String accountNumber, double balance, double availableBalance, List<UUID> transactions) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.availableBalance = availableBalance;
        this.closed = false;
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
    public double getAvailableBalance() { return this.availableBalance; }

    /**
     * Determine whether or not the account is closed
     *
     * @return the overdraft limit
     */
    public boolean getOverdraftLimit() { return this.closed; }

    /**
     * Retrieve the transactions.
     *
     * @return the transactions
     */
    public List<UUID> getTransactions() { return this.transactions; }

    /**
     * Set the account number.
     *
     * @param accountNumber     the account number
     */
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    /**
     * Set the account balance.
     *
     * @param balance     the account balance
     */
    public void setAccountBalance(double balance) { this.balance = balance; }

    /**
     * Set the minimum deposit.
     *
     * @param availableBalance     the minimum deposit
     */
    public void setAvailableBalance(double availableBalance) { this.availableBalance = availableBalance; }

    /**
     * Set the overdraft limit.
     */
    public void closeAccount() { this.closed = true; }

    /**
     * Set the overdraft limit.
     */
    public void openAccount() { this.closed = false; }

    /**
     * Set the transaction list.
     *
     * @param transactions      the transaction list
     */
    public void setTransactions(List<UUID> transactions) { this.transactions = transactions; }

    /**
     * Add a new transaction to the account.
     *
     * @param transactionId       the transaction id to be added
     */
    public void addTransaction(UUID transactionId) {
        transactions.add(transactionId);
    }


}
