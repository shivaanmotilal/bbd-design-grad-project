package za.co.bbd.wallet.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import za.co.bbd.wallet.dto.AccountDto;
import za.co.bbd.wallet.dto.CustomerDto;
import za.co.bbd.wallet.dto.NewCustomerDto;
import za.co.bbd.wallet.dto.TransactionDto;
import za.co.bbd.wallet.entity.CustomerEntity;
import za.co.bbd.wallet.entity.TransactionEntity;
import za.co.bbd.wallet.exceptions.BadRequestException;
import za.co.bbd.wallet.exceptions.ForbiddenException;
import za.co.bbd.wallet.exceptions.NotFoundException;
import za.co.bbd.wallet.repository.AccountRepository;
import za.co.bbd.wallet.repository.CustomerRepository;
import za.co.bbd.wallet.repository.TransactionRepository;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.*;
import java.util.stream.Collectors;

@RestController("wallet.api.VirtualWalletController")
@RequestMapping(value = "/wallet/api")
@WebService(serviceName = "VirtualWallet", name = "VirtualWallet", targetNamespace = "http://virtual.wallet.bbd")
@Slf4j
@SuppressWarnings("Duplicates")
public class VirtualWalletController {
    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    @Autowired
    public VirtualWalletController(
            @Qualifier("wallet.AccountRepository") AccountRepository accountRepository,
            @Qualifier("wallet.CustomerRepository") CustomerRepository customerRepository,
            @Qualifier("wallet.TransactionRepository") TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @ApiOperation(value = "Retrieve customer details", notes = "Retrieve customer details")
    @RequestMapping(value = "/customer/{customer-id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @WebMethod(operationName = "GetUser")
    @WebResult(name = "Customer")
    public CustomerDto getUser(
            @ApiParam(name = "customer-id", value = "The customer id", required = true)
            @PathVariable(name = "customer-id") String customerId,
            @ApiParam(name = "password", value = "The customer password", required = true)
            @RequestHeader(name = "password") String password)
            throws ForbiddenException, NotFoundException {

        var customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()) {
            throw new NotFoundException("Customer not Found");
        }
        var customer = customerOptional.get();
        if (!customer.getPassword().equals(password)){
            throw new ForbiddenException("Incorrect Password");
        }
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

    @ApiOperation(value = "Retrieve user accounts", notes = "Retrieve user accounts")
    @RequestMapping(value = "/accounts/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @WebMethod(operationName = "GetAccounts")
    @WebResult(name = "Account")
    public List<AccountDto> getAccounts(
            @ApiParam(name = "user-id", value = "The user id", required = true)
            @RequestHeader(name = "user-id") String userId,
            @ApiParam(name = "password", value = "The user password", required = true)
            @RequestHeader(name = "password") String password)
            throws ForbiddenException, NotFoundException {

        var customerOptional = customerRepository.findById(userId);
        if (customerOptional.isEmpty()) {
            throw new NotFoundException("Customer not Found");
        }
        var customer = customerOptional.get();
        if (!customer.getPassword().equals(password)){
            throw new ForbiddenException("Incorrect Password");
        }
        return customer.getAccounts().stream().map(account ->
                        AccountDto.builder()
                                .accountNumber(account.getAccountNumber())
                                .balance(account.getBalance())
                                .availableBalance(account.getAvailableBalance())
                                .closed(account.isClosed())
                                .transactions(account.getTransactions().stream().map(
                                        transactionEntity -> UUID.fromString(transactionEntity.getTransactionId())
                                ).collect(Collectors.toList()))
                                .build()
                ).collect(Collectors.toList());
    }

    @ApiOperation(value = "Retrieve specific user account", notes = "Retrieve specific user account")
    @RequestMapping(value = "/accounts/{account-number}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @WebMethod(operationName = "GetAccount")
    @WebResult(name = "Account")
    public AccountDto getAccount(
            @ApiParam(name = "user-id", value = "The user id", required = true)
            @RequestHeader(name = "user-id") String userId,
            @ApiParam(name = "password", value = "The user password", required = true)
            @RequestHeader(name = "password") String password,
            @ApiParam(name = "account-number", value = "The account number", required = true)
            @PathVariable(value = "account-number") String accountNumber)
            throws ForbiddenException, NotFoundException {

        var customerOptional = customerRepository.findById(userId);
        if (customerOptional.isEmpty()) {
            throw new NotFoundException("Customer not Found");
        }
        var customer = customerOptional.get();
        if (!customer.getPassword().equals(password)){
            throw new ForbiddenException("Incorrect Password");
        }

        var accountOptional =  customer.getAccounts().stream().filter(accountEntity -> accountEntity.getAccountNumber().equals(accountNumber)).findFirst();
        if (accountOptional.isEmpty()) {
            throw new NotFoundException("Account not Found");
        }
        var account = accountOptional.get();
        return AccountDto.builder()
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .availableBalance(account.getAvailableBalance())
                .closed(account.isClosed())
                .transactions(account.getTransactions().stream().map(
                        transactionEntity -> UUID.fromString(transactionEntity.getTransactionId())
                ).collect(Collectors.toList()))
                .build();
    }

    @ApiOperation(value = "Retrieve transactions for a specific account", notes = "Retrieve transactions for a specific account")
    @RequestMapping(value = "/transactions/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @WebMethod(operationName = "GetTransactions")
    @WebResult(name = "Transaction")
    public List<TransactionDto> getTransactions(
            @ApiParam(name = "user-id", value = "The user id", required = true)
            @RequestHeader(name = "user-id") String userId,
            @ApiParam(name = "password", value = "The user password", required = true)
            @RequestHeader(name = "password") String password,
            @ApiParam(name = "account-number", value = "The account number", required = true)
            @RequestHeader(name = "account-number") String accountNumber)
            throws ForbiddenException, NotFoundException {

        var customerOptional = customerRepository.findById(userId);
        if (customerOptional.isEmpty()) {
            throw new NotFoundException("Customer not Found");
        }
        var customer = customerOptional.get();
        if (!customer.getPassword().equals(password)){
            throw new ForbiddenException("Incorrect Password");
        }

        var accountOptional =  customer.getAccounts().stream().filter(accountEntity -> accountEntity.getAccountNumber().equals(accountNumber)).findFirst();
        if (accountOptional.isEmpty()) {
            throw new NotFoundException("Account not Found");
        }

        var transactionIds = accountOptional.get().getTransactions();

        var transactionEntityIterator = transactionRepository.findAllById(transactionIds.stream().map(transactionEntity -> {
            return transactionEntity.getTransactionId();
        }).collect(Collectors.toList())).iterator();

        List<TransactionDto> transactionDtos = new ArrayList<>();
        while (transactionEntityIterator.hasNext()) {
            TransactionEntity entity = transactionEntityIterator.next();
            transactionDtos.add(TransactionDto.builder()
                    .amount(entity.getAmount())
                    .dateInitiation(entity.getDateInitiation().toLocalDate().toString())
                    .dateSettlement(entity.getDateSettlement().toLocalDate().toString())
                    .fromAccountNumber(entity.getFromAccountNumber())
                    .fromAccountOpeningBalance(entity.getFromAccountOpeningBalance())
                    .settled(entity.isSettled())
                    .toAccountNumber(entity.getToAccountNumber())
                    .toAccountOpeningBalance(entity.getToAccountOpeningBalance())
                    .transactionId(UUID.fromString(entity.getTransactionId()))
                    .build());
        }

        if (transactionDtos.isEmpty()) {
            throw new NotFoundException("Transactions not Found");
        }

        return transactionDtos;
    }


    @ApiOperation(value = "Create a new Customer", notes = "Create a new Customer")
    @RequestMapping(value = "/customer/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @WebMethod(operationName = "CreateCustomer")
    @WebResult(name = "Customer")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ResponseBody
    public CustomerDto createUser(
            @ApiParam(name = "new-customer", value = "Request to make new customer", required = true)
            @RequestBody NewCustomerDto newCustomerDto)
            throws BadRequestException {

        if (!newCustomerDto.getPassword().equals(newCustomerDto.getConfirmedPassword())) {
            throw new BadRequestException("Passwords do not match");
        }

        Random rand = new Random();
        String userId = "5" + String.format("%07d", rand.nextInt(1000000));

        Optional<CustomerEntity> customerOptional = customerRepository.findById(userId);
        while (customerOptional.isPresent()) {
            userId = "5" + String.format("%07d", rand.nextInt(1000000));
            customerOptional = customerRepository.findById(userId);
        }

        CustomerDto customerDto = CustomerDto.builder()
                .customerId(userId)
                .firstName(newCustomerDto.getFirstName())
                .surname(newCustomerDto.getSurname())
                .email(newCustomerDto.getEmail())
                .phoneNumber(newCustomerDto.getPhoneNumber())
                .password(newCustomerDto.getPassword())
                .accountDtos(new ArrayList<>())
                .build();

        CustomerEntity customerEntity = CustomerEntity.builder()
                .customerId(userId)
                .firstName(newCustomerDto.getFirstName())
                .surname(newCustomerDto.getSurname())
                .email(newCustomerDto.getEmail())
                .phoneNumber(newCustomerDto.getPhoneNumber())
                .password(newCustomerDto.getPassword())
                .accounts(new ArrayList<>())
                .build();

        customerRepository.save(customerEntity);

        return customerDto;
    }

}
