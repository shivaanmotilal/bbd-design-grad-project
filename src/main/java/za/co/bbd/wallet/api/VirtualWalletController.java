package za.co.bbd.wallet.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import za.co.bbd.wallet.dto.*;
import za.co.bbd.wallet.entity.CustomerEntity;
import za.co.bbd.wallet.entity.TransactionEntity;
import za.co.bbd.wallet.exceptions.BadRequestException;
import za.co.bbd.wallet.exceptions.ForbiddenException;
import za.co.bbd.wallet.exceptions.NotFoundException;
import za.co.bbd.wallet.exceptions.UnauthorizedException;
import za.co.bbd.wallet.repository.AccountRepository;
import za.co.bbd.wallet.repository.CustomerRepository;
import za.co.bbd.wallet.repository.TransactionRepository;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController("wallet.api.VirtualWalletController")
@RequestMapping(value = "/wallet/api")
@WebService(serviceName = "VirtualWallet", name = "VirtualWallet", targetNamespace = "http://virtual.wallet.bbd")
@Slf4j
@SuppressWarnings("Duplicates")
public class VirtualWalletController {
    Logger LOGGER = LoggerFactory.getLogger(VirtualWalletController.class);
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
        LOGGER.info("Starting up VirtualWalletController");

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
            LOGGER.info("CUSTOMER (" + customerId +") NOT FOUND");
            throw new NotFoundException("Customer not Found");
        }
        var customer = customerOptional.get();
        if (!customer.getPassword().equals(password)){
            LOGGER.info("INCORRECT PASSWORD FOR (" + customerId +")");
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
                            .balance(account.getAccountBalance())
                            .availableBalance(account.getAvailableBalance())
                            .closed(account.isClosedAccount())
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
            LOGGER.info("USER (" + userId +") NOT FOUND");
            throw new NotFoundException("Customer not Found");
        }
        var customer = customerOptional.get();
        if (!customer.getPassword().equals(password)){
            LOGGER.info("INCORRECT PASSWORD FOR (" + userId +")");
            throw new ForbiddenException("Incorrect Password");
        }
        return customer.getAccounts().stream().map(account ->
                        AccountDto.builder()
                                .accountNumber(account.getAccountNumber())
                                .balance(account.getAccountBalance())
                                .availableBalance(account.getAvailableBalance())
                                .closed(account.isClosedAccount())
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
            LOGGER.info("CUSTOMER (" + userId +") NOT FOUND");
            throw new NotFoundException("Customer not Found");
        }
        var customer = customerOptional.get();
        if (!customer.getPassword().equals(password)){
            LOGGER.info("INCORRECT PASSWORD FOR (" + customer.toString() +")");
            throw new ForbiddenException("Incorrect Password");
        }

        var accountOptional =  customer.getAccounts().stream().filter(accountEntity -> accountEntity.getAccountNumber().equals(accountNumber)).findFirst();
        if (accountOptional.isEmpty()) {
            LOGGER.info("ACCOUNT (" + accountOptional +") NOT FOUND");
            throw new NotFoundException("Account not Found");
        }
        var account = accountOptional.get();
        return AccountDto.builder()
                .accountNumber(account.getAccountNumber())
                .balance(account.getAccountBalance())
                .availableBalance(account.getAvailableBalance())
                .closed(account.isClosedAccount())
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
            LOGGER.info("USER (" + userId +") NOT FOUND");
            throw new NotFoundException("Customer not Found");
        }
        var customer = customerOptional.get();
        if (!customer.getPassword().equals(password)){
            LOGGER.info("INCORRECT PASSWORD FOR (" + userId +")");
            throw new ForbiddenException("Incorrect Password");
        }

        var accountOptional =  customer.getAccounts().stream().filter(accountEntity -> accountEntity.getAccountNumber().equals(accountNumber)).findFirst();
        if (accountOptional.isEmpty()) {
            LOGGER.info("CUSTOMER (" + accountOptional +") NOT FOUND");
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
            LOGGER.info("NO TRANSACTIONS FOUND");
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
            LOGGER.info("PASSWORDS DO NOT MATCH");
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

    @ApiOperation(value = "Get payment authorization", notes = "Get authorization for a payment")
    @RequestMapping(value = "/payment/authorization/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @WebMethod(operationName = "CreateAuthorization")
    @WebResult(name = "Transaction")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public TransactionDto doAuthorization(
            @ApiParam(name = "customer-id", value = "The customer id", required = true)
            @RequestHeader(name = "customer-id") String customerId,
            @ApiParam(name = "password", value = "The user password", required = true)
            @RequestHeader(name = "password") String password,
            @ApiParam(name = "authorization", value = "Request to authorize a payment", required = true)
            @RequestBody AuthorizationDto authorizationDto)
            throws NotFoundException, UnauthorizedException, ForbiddenException {


        if (authorizationDto.getAmount() < 0) {
            throw new ForbiddenException("Y U TAK MAH MUNNY");
        }

        var customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()) {
            LOGGER.info("CUSTOMER (" + customerId +") NOT FOUND");
            throw new NotFoundException("Customer not found");
        }
        var customer = customerOptional.get();
        if (!customer.getPassword().equals(password)){
            LOGGER.info("AUTHORIZATION FAILED. INCORRECT PASSWORD");
            throw new UnauthorizedException("Authorization failed due to incorrect password");
        }

        var fromAccountOptional =  customer.getAccounts().stream()
                .filter(accountEntity -> accountEntity.getAccountNumber().equals(authorizationDto.getFromAccountNumber()))
                .findFirst();
        if (fromAccountOptional.isEmpty()) {
            throw new NotFoundException("Customer account not found");
        }
        var fromAccount = fromAccountOptional.get();

        if (fromAccount.getAvailableBalance() < authorizationDto.getAmount()) {
            throw new UnauthorizedException("Authorization failed due to insufficient funds");
        }

        var toAccountOptional = accountRepository.findById(authorizationDto.getToAccountNumber());
        if (toAccountOptional.isEmpty()) {
            throw new NotFoundException("Beneficiary account not found");
        }
        var toAccount = toAccountOptional.get();

        fromAccount.setAvailableBalance(fromAccount.getAvailableBalance()-authorizationDto.getAmount());
        accountRepository.save(fromAccount);

        toAccount.setAccountBalance(toAccount.getAccountBalance()+authorizationDto.getAmount());
        accountRepository.save(toAccount);

        var transactionId = UUID.randomUUID();

        TransactionDto transactionDto = TransactionDto.builder()
                .amount(authorizationDto.getAmount())
                .dateInitiation(LocalDate.now().toString())
                .dateSettlement(LocalDate.now().toString())
                .fromAccountNumber(fromAccount.getAccountNumber())
                .fromAccountOpeningBalance(fromAccount.getAvailableBalance())
                .settled(false)
                .toAccountNumber(toAccount.getAccountNumber())
                .toAccountOpeningBalance(toAccount.getAvailableBalance())
                .transactionId(transactionId)
                .build();

        TransactionEntity transactionEntity = TransactionEntity.builder()
                .amount(authorizationDto.getAmount())
                .dateInitiation(Date.valueOf(LocalDate.now()))
                .dateSettlement(Date.valueOf(LocalDate.now()))
                .fromAccountNumber(fromAccount.getAccountNumber())
                .fromAccountOpeningBalance(fromAccount.getAvailableBalance())
                .settled(false)
                .toAccountNumber(toAccount.getAccountNumber())
                .toAccountOpeningBalance(toAccount.getAvailableBalance())
                .transactionId(transactionId.toString())
                .build();

        transactionRepository.save(transactionEntity);

        return transactionDto;

    }

    @ApiOperation(value = "Settle", notes = "Settle payment")
    @RequestMapping(value = "/payment/settlement/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @WebMethod(operationName = "SettleTransaction")
    @WebResult(name = "Transaction")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public TransactionDto settleTransaction(
            @ApiParam(name = "transaction-settlement", value = "The transaction id to be settled", required = true)
            @RequestBody TransactionSettlementDto transactionSettlementDto)
            throws NotFoundException, UnauthorizedException, ForbiddenException {

        var transactionOptional = transactionRepository.findById(transactionSettlementDto.getTransactionId().toString());
        if (transactionOptional.isEmpty()) {
            throw new NotFoundException("Transaction not found");
        }
        var transaction = transactionOptional.get();

        var fromAccountOptional = accountRepository.findById(transaction.getFromAccountNumber());
        if (fromAccountOptional.isEmpty()) {
            throw new NotFoundException("Payer account not found");
        }
        var fromAccount = fromAccountOptional.get();

        var toAccountOptional = accountRepository.findById(transaction.getToAccountNumber());
        if (toAccountOptional.isEmpty()) {
            throw new NotFoundException("Beneficiary account not found");
        }
        var toAccount = toAccountOptional.get();

        fromAccount.setAccountBalance(fromAccount.getAccountBalance()-transaction.getAmount());
        toAccount.setAvailableBalance(toAccount.getAvailableBalance()-transaction.getAmount());
        transaction.setSettled(true);

        TransactionDto transactionDto = TransactionDto.builder()
                .amount(transaction.getAmount())
                .dateInitiation(transaction.getDateInitiation().toString())
                .dateSettlement(transaction.getDateSettlement().toString())
                .fromAccountNumber(transaction.getFromAccountNumber())
                .fromAccountOpeningBalance(transaction.getFromAccountOpeningBalance())
                .settled(transaction.isSettled())
                .toAccountNumber(transaction.getToAccountNumber())
                .toAccountOpeningBalance(transaction.getToAccountOpeningBalance())
                .transactionId(UUID.fromString(transaction.getTransactionId()))
                .build();

        transactionRepository.save(transaction);

        return transactionDto;

    }
}
