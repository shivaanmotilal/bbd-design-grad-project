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
import za.co.bbd.wallet.entity.PaymentEntity;
import za.co.bbd.wallet.exceptions.BadRequestException;
import za.co.bbd.wallet.exceptions.ForbiddenException;
import za.co.bbd.wallet.exceptions.NotFoundException;
import za.co.bbd.wallet.exceptions.UnauthorizedException;
import za.co.bbd.wallet.services.AccountService;
import za.co.bbd.wallet.services.AuthorizationService;
import za.co.bbd.wallet.services.CustomerService;
import za.co.bbd.wallet.services.PaymentService;

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
    Logger LOGGER = LoggerFactory.getLogger(VirtualWalletController.class);
    private CustomerService customerService;
    private AccountService accountService;
    private PaymentService PaymentService;
    private AuthorizationService authorizationService;

    @Autowired
    public VirtualWalletController(
            @Qualifier("wallet.CustomerService") CustomerService customerService,
            @Qualifier("wallet.AccountService") AccountService accountService,
            @Qualifier("wallet.PaymentService") PaymentService PaymentService,
            @Qualifier("wallet.AuthorizationService") AuthorizationService authorizationService) {
        this.customerService= customerService;
        this.accountService=accountService;
        this.PaymentService=PaymentService;
        this.authorizationService= authorizationService;
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
        var customer= customerService.findCustomer(customerId,password);
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
                            .closed(account.getClosedAccount())
                            .Payments(account.getPayments().stream().map(
                                PaymentEntity -> UUID.fromString(PaymentEntity.getPaymentId())
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
        var customer= customerService.findCustomer(userId,password);
        return customer.getAccounts().stream().map(account ->
                        AccountDto.builder()
                                .accountNumber(account.getAccountNumber())
                                .balance(account.getAccountBalance())
                                .availableBalance(account.getAvailableBalance())
                                .closed(account.getClosedAccount())
                                .Payments(account.getPayments().stream().map(
                                        PaymentEntity -> UUID.fromString(PaymentEntity.getPaymentId())
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

        var customer= customerService.findCustomer(userId,password);
        var account= accountService.findAccount(customer, accountNumber);

        return AccountDto.builder()
                .accountNumber(account.getAccountNumber())
                .balance(account.getAccountBalance())
                .availableBalance(account.getAvailableBalance())
                .closed(account.getClosedAccount())
                .Payments(account.getPayments().stream().map(
                        PaymentEntity -> UUID.fromString(PaymentEntity.getPaymentId())
                ).collect(Collectors.toList()))
                .build();
    }

    @ApiOperation(value = "Retrieve Payments for a specific account", notes = "Retrieve Payments for a specific account")
    @RequestMapping(value = "/Payments/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @WebMethod(operationName = "GetPayments")
    @WebResult(name = "Payment")
    public List<PaymentDto> getPayments(
            @ApiParam(name = "user-id", value = "The user id", required = true)
            @RequestHeader(name = "user-id") String userId,
            @ApiParam(name = "password", value = "The user password", required = true)
            @RequestHeader(name = "password") String password,
            @ApiParam(name = "account-number", value = "The account number", required = true)
            @RequestHeader(name = "account-number") String accountNumber)
            throws ForbiddenException, NotFoundException {
        var customer= customerService.findCustomer(userId,password);
        var PaymentEntityIterator= PaymentService.getUserPayments(customer, accountNumber);

        List<PaymentDto> PaymentDtos = new ArrayList<>();
        while (PaymentEntityIterator.hasNext()) {
            PaymentEntity entity = PaymentEntityIterator.next();
            PaymentDtos.add(PaymentDto.builder()
                    .amount(entity.getAmount())
                    .dateInitiation(entity.getDateInitiation().toLocalDate().toString())
                    .dateSettlement(entity.getDateSettlement().toLocalDate().toString())
                    .fromAccountNumber(entity.getFromAccountNumber())
                    .fromAccountOpeningBalance(entity.getFromAccountOpeningBalance())
                    .settled(entity.getSettled())
                    .toAccountNumber(entity.getToAccountNumber())
                    .toAccountOpeningBalance(entity.getToAccountOpeningBalance())
                    .PaymentId(UUID.fromString(entity.getPaymentId()))
                    .build());
        }

        if (PaymentDtos.isEmpty()) {
            LOGGER.info("NO PaymentS FOUND");
            throw new NotFoundException("Payments not Found");
        }

        return PaymentDtos;
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

        customerService.checkPassword(newCustomerDto);
        var userId= customerService.createCustomerUserId();
        var customerDto= customerService.saveCustomer(newCustomerDto, userId);
        return customerDto;
    }

    @ApiOperation(value = "Get Payment authorization", notes = "Get authorization for a Payment")
    @RequestMapping(value = "/Payment/authorization/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @WebMethod(operationName = "CreateAuthorization")
    @WebResult(name = "Payment")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public PaymentDto doAuthorization(
            @ApiParam(name = "customer-id", value = "The customer id", required = true)
            @RequestHeader(name = "customer-id") String customerId,
            @ApiParam(name = "password", value = "The user password", required = true)
            @RequestHeader(name = "password") String password,
            @ApiParam(name = "authorization", value = "Request to authorize a Payment", required = true)
            @RequestBody AuthorizationDto authorizationDto)
            throws NotFoundException, UnauthorizedException, ForbiddenException {

        var customer= authorizationService.checkAuthorisation(customerId,password);
        var fromAccount= accountService.findFromAccount(customer);
        accountService.checkFunds(fromAccount);
        var toAccount= accountService.findBeneficiaryAccount();
        accountService.updateAvailableCustomerBalance(fromAccount);
        accountService.updateBeneficiaryAccountBalance(toAccount);
        var PaymentDto= PaymentService.savePayment(fromAccount, toAccount);

        return PaymentDto;

    }

    @ApiOperation(value = "Settle", notes = "Settle Payment")
    @RequestMapping(value = "/Payment/settlement/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @WebMethod(operationName = "SettlePayment")
    @WebResult(name = "Payment")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public PaymentDto settlePayment(
            @ApiParam(name = "Payment-settlement", value = "The Payment id to be settled", required = true)
            @RequestBody PaymentSettlementDto PaymentSettlementDto)
            throws NotFoundException, UnauthorizedException, ForbiddenException {

        var Payment= PaymentService.findPayment(PaymentSettlementDto);
        var fromAccount = accountService.findPayerAccount(Payment);
        var toAccount = accountService.findReceiverAccount(Payment);
        var PaymentDto= PaymentService.settlePayment(fromAccount, toAccount, Payment);

        return PaymentDto;

    }
}
