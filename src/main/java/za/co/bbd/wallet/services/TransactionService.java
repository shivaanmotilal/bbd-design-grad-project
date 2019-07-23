package za.co.bbd.wallet.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.bbd.wallet.dto.AuthorizationDto;
import za.co.bbd.wallet.dto.TransactionDto;
import za.co.bbd.wallet.dto.TransactionSettlementDto;
import za.co.bbd.wallet.entity.AccountEntity;
import za.co.bbd.wallet.entity.CustomerEntity;
import za.co.bbd.wallet.entity.TransactionEntity;
import za.co.bbd.wallet.exceptions.NotFoundException;
import za.co.bbd.wallet.repository.TransactionRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("wallet.TransactionService")
public class TransactionService {
    Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);
    private TransactionRepository transactionRepository;
    private AuthorizationDto authorizationDto;

    public Iterator<TransactionEntity> getUserTransactions(CustomerEntity customer, String accountNumber) throws NotFoundException {

        var accountOptional = customer.getAccounts().stream().filter(accountEntity -> accountEntity.getAccountNumber().equals(accountNumber)).findFirst();
        if (accountOptional.isEmpty()) {
            LOGGER.info("CUSTOMER (" + accountOptional + ") NOT FOUND");
            throw new NotFoundException("Account not Found");
        }

        var transactionIds = accountOptional.get().getTransactions();

        var transactionEntityIterator = transactionRepository.findAllById(transactionIds.stream().map(transactionEntity -> {
            return transactionEntity.getTransactionId();
        }).collect(Collectors.toList())).iterator();

        return transactionEntityIterator;
    }

    public TransactionEntity findTransaction(TransactionSettlementDto transactionSettlementDto) throws NotFoundException{
        var transactionOptional = transactionRepository.findById(transactionSettlementDto.getTransactionId().toString());
        if (transactionOptional.isEmpty()) {
            throw new NotFoundException("Transaction not found");
        }
        var transaction = transactionOptional.get();
        return transaction;
    }

    public TransactionDto saveTransaction(AccountEntity fromAccount, AccountEntity toAccount){
        var transactionId = UUID.randomUUID();

        TransactionDto transactionDto = TransactionDto.builder()
                .amount(authorizationDto.getAmount())
                .dateInitiation(LocalDate.now().toString())
                .dateSettlement(LocalDate.now().toString())
                .fromAccountNumber(fromAccount.getAccountNumber())
                .fromAccountOpeningBalance(fromAccount.getAvailableBalance())
                .settled(0)
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
                .settled(0)
                .toAccountNumber(toAccount.getAccountNumber())
                .toAccountOpeningBalance(toAccount.getAvailableBalance())
                .transactionId(transactionId.toString())
                .build();

        transactionRepository.save(transactionEntity);

        return transactionDto;
    }

    public TransactionDto settleTransaction(AccountEntity fromAccount, AccountEntity toAccount, TransactionEntity transaction){
        fromAccount.setAccountBalance(fromAccount.getAccountBalance()-transaction.getAmount());
        toAccount.setAvailableBalance(toAccount.getAvailableBalance()-transaction.getAmount());
        transaction.setSettled(0);

        TransactionDto transactionDto = TransactionDto.builder()
                .amount(transaction.getAmount())
                .dateInitiation(transaction.getDateInitiation().toString())
                .dateSettlement(transaction.getDateSettlement().toString())
                .fromAccountNumber(transaction.getFromAccountNumber())
                .fromAccountOpeningBalance(transaction.getFromAccountOpeningBalance())
                .settled(transaction.getSettled())
                .toAccountNumber(transaction.getToAccountNumber())
                .toAccountOpeningBalance(transaction.getToAccountOpeningBalance())
                .transactionId(UUID.fromString(transaction.getTransactionId()))
                .build();

        transactionRepository.save(transaction);
        return transactionDto;
    }
}
