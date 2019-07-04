package za.co.bbd.wallet.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import za.co.bbd.wallet.dto.AccountDto;
import za.co.bbd.wallet.dto.CustomerDto;
import za.co.bbd.wallet.dto.TransactionDto;
import za.co.bbd.wallet.repository.AccountRepository;
import za.co.bbd.wallet.repository.CustomerRepository;

import javax.annotation.PostConstruct;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.*;
import java.util.stream.Collectors;

@RestController("wallet.api.VirtualWalletController")
@RequestMapping(value = "/wallet/api")
@WebService(serviceName = "VirtualWallet", name = "VirtualWallet", targetNamespace = "http://virtual.wallet.bbd")
@Slf4j
public class VirtualWalletController {
    private List<CustomerDto> customerDtos = new ArrayList<>();
    private List<TransactionDto> transactionDtos = new ArrayList<>();
    private List<AccountDto> accountDtos = new ArrayList<>();

    private AccountDto accountDto;

    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;

    @Autowired
    public VirtualWalletController(@Qualifier("wallet.CustomerRepository") CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    //This will 100% be removed:
    @PostConstruct
    public void fakeDataSetup() {
        accountDto = new AccountDto();
//        accountDto.setAccountName("Spoils of War");
        accountDto.setAccountNumber("10001284657");
        accountDto.setBalance(1000000);
        accountDto.setAvailableBalance(1000);
//        accountDto.setOverdraftLimit(3000);
        accountDto.setTransactions(new ArrayList<>());

        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId("5000123");
        customerDto.setFirstName("The Lauren");
        customerDto.setSurname("Barger");
        customerDto.setPhoneNumber("0716823276");
        customerDto.setEmail("lauren@bbd.co.za");
        customerDto.setPassword("1234");
        customerDto.setAccountDtos(Collections.singletonList(accountDto));

        customerDtos.add(customerDto);
    }

    @ApiOperation(value = "Retrieve the accountDto details for a specific accountDto number", notes = "Retrieve the information associated with a specific accountDto")
    @RequestMapping(value = "/accountDto/{accountDto-number}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @WebMethod(operationName = "GetAccount")
    @WebResult(name = "AccountEntity")
    public AccountDto getAccount(
            @ApiParam(name = "accountDto-number", value = "The accountDto number", required = true)
            @PathVariable(name = "accountDto-number") String accountNumber) {

        if (accountNumber.equals(accountDto.getAccountNumber())) {
            return accountDto;
        } else {
            return null;
        }
    }

    @ApiOperation(value = "Create a new CustomerEntity", notes = "Create a new CustomerEntity")
    @RequestMapping(value = "/user/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @WebMethod(operationName = "CreateUser")
    @WebResult(name = "AccountEntity")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created") ,
            @ApiResponse(code = 302, message = "CustomerEntity already exists") ,
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

        CustomerDto customerDto = CustomerDto.builder()
                .customerId(userId)
                .firstName(firstName)
                .surname(surname)
                .email(email)
                .phoneNumber(phoneNumber)
                .password(password)
                .accountDtos(new ArrayList<>())
                .build();

        if (customerDtos.stream().filter(u -> u.equals(customerDto)).findFirst().orElse(null) != null) {
            return null;
        }

        if (!password.equals(confirmPassword)) {
            return null;
        }

        customerDtos.add(customerDto);

        return customerDto.getCustomerId();
    }

    @ApiOperation(value = "Retrieve user details", notes = "Retrieve user details")
    @RequestMapping(value = "/user/{user-id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @WebMethod(operationName = "GetUser")
    @WebResult(name = "CustomerEntity")
    public CustomerDto getUser(
            @ApiParam(name = "user-id", value = "The user id", required = true)
            @PathVariable(name = "user-id") String userId,
            @ApiParam(name = "password", value = "The user password", required = true)
            @RequestHeader(name = "password") String password){

        var customerOptional = customerRepository.findById(userId);
        if (customerOptional.isEmpty()) {
            return null;
        }
        var customer = customerOptional.get();

        return CustomerDto.builder()
                .customerId(customer.getCustomerId())
                .password(customer.getPassword())
                .firstName(customer.getFirstName())
                .surname(customer.getSurname())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .accountDtos(customer.getAccounts().stream().map(account ->
                        AccountDto.builder()
                            .accountNumber(account.getAccountNumber())
                            .balance(account.getBalance())
                            .availableBalance(account.getAvailableBalance())
                            .closed(account.isClosed())
                            .transactions(account.getTransactions().stream().map(
                                transactionEntity -> UUID.fromString(transactionEntity.getTransactionId())
                            ).collect(Collectors.toList()))
                            .build()
                ).collect(Collectors.toList()))
                .build();
    }

}
