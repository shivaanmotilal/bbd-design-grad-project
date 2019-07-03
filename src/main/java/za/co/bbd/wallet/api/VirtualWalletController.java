package za.co.bbd.wallet.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.co.bbd.wallet.model.Account;
import za.co.bbd.wallet.model.User;

import javax.annotation.PostConstruct;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.ArrayList;

@RestController("wallet.api.VirtualWalletController")
@RequestMapping(value = "/wallet/api")
@WebService(serviceName = "VirtualWallet", name = "VirtualWallet", targetNamespace = "http://virtual.wallet.bbd")
@Slf4j
public class VirtualWalletController {

    private User user;
    private Account account;

    //This will 100% be removed:
    @PostConstruct
    public void fakeDataSetup() {
        this.account = new Account();
        this.account.setAccountName("Spoils of War");
        this.account.setAccountNumber("10001284657");
        this.account.setBalance(1000000);
        this.account.setMinimumDeposit(1000);
        this.account.setOverdraftLimit(3000);
        this.account.setTransactions(new ArrayList<>());

        this.user = new User();
        user.setFirstName("The Lauren");
        user.setSurname("Barger");
        user.setPhoneNumber("0716823276");
        user.setEmail("lauren@bbd.co.za");
        user.addAccounts(this.account);
    }

    @ApiOperation(value = "Retrieve the account details for a specific account number", notes = "Retrieve the information associated with a specific account")
    @RequestMapping(value = "/account/{account-number}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @WebMethod(operationName = "GetAccount")
    @WebResult(name = "Account")
    public Account getAccount(
            @ApiParam(name = "account-number", value = "The account number", required = true)
            @PathVariable(name = "account-number") String accountNumber) {

        if (accountNumber.equals(account.getAccountNumber())) {
            return account;
        } else {
            return null;
        }
    }

}
