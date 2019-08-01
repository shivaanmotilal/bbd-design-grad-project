package za.co.bbd.wallet.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.bbd.wallet.dto.AuthorizationDto;
import za.co.bbd.wallet.dto.PaymentDto;
import za.co.bbd.wallet.dto.PaymentSettlementDto;
import za.co.bbd.wallet.entity.AccountEntity;
import za.co.bbd.wallet.entity.CustomerEntity;
import za.co.bbd.wallet.entity.PaymentEntity;
import za.co.bbd.wallet.exceptions.NotFoundException;
import za.co.bbd.wallet.repository.PaymentRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("wallet.PaymentService")
public class PaymentService {
    Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);
    private PaymentRepository PaymentRepository;
    private AuthorizationDto authorizationDto;

    public Iterator<PaymentEntity> getUserPayments(CustomerEntity customer, String accountNumber) throws NotFoundException {

        var accountOptional = customer.getAccounts().stream().filter(accountEntity -> accountEntity.getAccountNumber().equals(accountNumber)).findFirst();
        if (accountOptional.isEmpty()) {
            LOGGER.info("CUSTOMER (" + accountOptional + ") NOT FOUND");
            throw new NotFoundException("Account not Found");
        }

        var PaymentIds = accountOptional.get().getPayments();

        var PaymentEntityIterator = PaymentRepository.findAllById(PaymentIds.stream().map(PaymentEntity -> {
            return PaymentEntity.getPaymentId();
        }).collect(Collectors.toList())).iterator();

        return PaymentEntityIterator;
    }

    public PaymentEntity findPayment(PaymentSettlementDto PaymentSettlementDto) throws NotFoundException{
        var PaymentOptional = PaymentRepository.findById(PaymentSettlementDto.getPaymentId().toString());
        if (PaymentOptional.isEmpty()) {
            throw new NotFoundException("Payment not found");
        }
        var Payment = PaymentOptional.get();
        return Payment;
    }

    public PaymentDto savePayment(AccountEntity fromAccount, AccountEntity toAccount){
        var PaymentId = UUID.randomUUID();

        PaymentDto paymentDto = PaymentDto.builder()
                .amount(authorizationDto.getAmount())
                .dateInitiation(LocalDate.now().toString())
                .dateSettlement(LocalDate.now().toString())
                .fromAccountNumber(fromAccount.getAccountNumber())
                .fromAccountOpeningBalance(fromAccount.getAvailableBalance())
                .settled(0)
                .toAccountNumber(toAccount.getAccountNumber())
                .toAccountOpeningBalance(toAccount.getAvailableBalance())
                .PaymentId(PaymentId)
                .build();

        PaymentEntity paymentEntity = PaymentEntity.builder()
                .amount(authorizationDto.getAmount())
                .dateInitiation(Date.valueOf(LocalDate.now()))
                .dateSettlement(Date.valueOf(LocalDate.now()))
                .fromAccountNumber(fromAccount.getAccountNumber())
                .fromAccountOpeningBalance(fromAccount.getAvailableBalance())
                .settled(0)
                .toAccountNumber(toAccount.getAccountNumber())
                .toAccountOpeningBalance(toAccount.getAvailableBalance())
                .PaymentId(PaymentId.toString())
                .build();

        PaymentRepository.save(paymentEntity);

        return paymentDto;
    }

    public PaymentDto settlePayment(AccountEntity fromAccount, AccountEntity toAccount, PaymentEntity Payment){
        fromAccount.setAccountBalance(fromAccount.getAccountBalance()-Payment.getAmount());
        toAccount.setAvailableBalance(toAccount.getAvailableBalance()-Payment.getAmount());
        Payment.setSettled(0);

        PaymentDto paymentDto = PaymentDto.builder()
                .amount(Payment.getAmount())
                .dateInitiation(Payment.getDateInitiation().toString())
                .dateSettlement(Payment.getDateSettlement().toString())
                .fromAccountNumber(Payment.getFromAccountNumber())
                .fromAccountOpeningBalance(Payment.getFromAccountOpeningBalance())
                .settled(Payment.getSettled())
                .toAccountNumber(Payment.getToAccountNumber())
                .toAccountOpeningBalance(Payment.getToAccountOpeningBalance())
                .PaymentId(UUID.fromString(Payment.getPaymentId()))
                .build();

        PaymentRepository.save(Payment);
        return paymentDto;
    }
}
