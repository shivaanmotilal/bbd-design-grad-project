package za.co.bbd.wallet.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import za.co.bbd.wallet.model.Account;
import za.co.bbd.wallet.model.Transaction;
import za.co.bbd.wallet.model.User;

import javax.annotation.PostConstruct;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController("wallet.api.VirtualWalletController")
@RequestMapping(value = "/wallet/api")
@WebService(serviceName = "VirtualWallet", name = "VirtualWallet", targetNamespace = "http://virtual.wallet.bbd")
@Slf4j
public class VirtualWalletController {
    private List<User> users = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();
    private List<Account> accounts = new ArrayList<>();

    private Account account;

    //This will 100% be removed:
    @PostConstruct
    public void fakeDataSetup() {
        account = new Account();
//        account.setAccountName("Spoils of War");
        account.setAccountNumber("10001284657");
        account.setBalance(1000000);
        account.setAvailableBalance(1000);
//        account.setOverdraftLimit(3000);
        account.setTransactions(new ArrayList<>());

        User user = new User();
        user.setUserId("5000123");
        user.setFirstName("The Lauren");
        user.setSurname("Barger");
        user.setPhoneNumber("0716823276");
        user.setEmail("lauren@bbd.co.za");
        user.setPassword("1234");
        user.addAccounts(account);

        users.add(user);
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

    @ApiOperation(value = "Create a new User", notes = "Create a new User")
    @RequestMapping(value = "/user/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @WebMethod(operationName = "CreateUser")
    @WebResult(name = "Account")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created") ,
            @ApiResponse(code = 302, message = "User already exists") ,
            @ApiResponse(code = 400, message = "Passwords do not match") ,
            @ApiResponse(code = 500, message = "The VirtualWallet is unable to process your request due to an internal service error") })
    public String createUser(
            @ApiParam(name = "first-name", value = "The first name of the user", required = true)
            @RequestHeader(name = "first-name") String firstName,
            @ApiParam(name = "surname", value = "The surname of the user", required = true)
            @RequestHeader(name = "surname") String surname,
            @ApiParam(name = "email", value = "The email address of the user", required = true)
            @RequestHeader(name = "email") String email,
            @ApiParam(name = "phone-number", value = "The phone number of the user", required = true)
            @RequestHeader(name = "phone-number") String phoneNumber,
            @ApiParam(name = "password", value = "The chosen password of the user", required = true)
            @RequestHeader(name = "password") String password,
            @ApiParam(name = "confirm-password", value = "The confirmation of the password chosen by the user", required = true)
            @RequestHeader(name = "confirm-password") String confirmPassword) {

        Random rand = new Random();
        String userId = "5000" + rand.nextInt(1000);

        User user = new User(userId, firstName, surname,email, phoneNumber, password, new ArrayList<>());

        if (users.stream().filter(u -> u.equals(user)).findFirst().orElse(null) != null) {
            return null;
        }

        if (!password.equals(confirmPassword)) {
            return null;
        }

        users.add(user);

        return user.getUserId();
    }

    @ApiOperation(value = "Retrieve user details", notes = "Retrieve user details")
    @RequestMapping(value = "/user/{user-id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @WebMethod(operationName = "GetUser")
    @WebResult(name = "User")
    public User getUser(
            @ApiParam(name = "user-id", value = "The user id", required = true)
            @PathVariable(name = "user-id") String userId,
            @ApiParam(name = "password", value = "The user password", required = true)
            @RequestHeader(name = "password") String password){

        return users.stream()
                .filter(u -> u.getUserId().equals(userId) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);

    }

}
