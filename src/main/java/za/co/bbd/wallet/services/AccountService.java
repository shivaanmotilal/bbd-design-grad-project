package za.co.bbd.wallet.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.bbd.wallet.api.VirtualWalletController;
import za.co.bbd.wallet.dto.AuthorizationDto;
import za.co.bbd.wallet.entity.AccountEntity;
import za.co.bbd.wallet.entity.CustomerEntity;
import za.co.bbd.wallet.entity.TransactionEntity;
import za.co.bbd.wallet.exceptions.NotFoundException;
import za.co.bbd.wallet.exceptions.UnauthorizedException;
import za.co.bbd.wallet.repository.AccountRepository;

@Service("wallet.AccountService")
public class AccountService {
    private AccountRepository accountRepository;
    Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
    AuthorizationDto authorizationDto;

    public AccountEntity findAccount(CustomerEntity customer, String accountNumber) throws NotFoundException{
        var accountOptional =  customer.getAccounts().stream().filter(accountEntity -> accountEntity.getAccountNumber().equals(accountNumber)).findFirst();
        if (accountOptional.isEmpty()) {
            LOGGER.info("ACCOUNT (" + accountOptional +") NOT FOUND");
            throw new NotFoundException("Account not Found");
        }
        var account = accountOptional.get();

        return account;
    }

    public AccountEntity findFromAccount(CustomerEntity customer) throws NotFoundException{
        var fromAccountOptional =  customer.getAccounts().stream()
                .filter(accountEntity -> accountEntity.getAccountNumber().equals(authorizationDto.getFromAccountNumber()))
                .findFirst();
        if (fromAccountOptional.isEmpty()) {
            throw new NotFoundException("Customer account not found");
        }
        var fromAccount = fromAccountOptional.get();
        return fromAccount;
    }

    public AccountEntity findPayerAccount(TransactionEntity transaction) throws NotFoundException{
        var fromAccountOptional = accountRepository.findById(transaction.getFromAccountNumber());
        if (fromAccountOptional.isEmpty()) {
            throw new NotFoundException("Payer account not found");
        }
        var fromAccount = fromAccountOptional.get();
        return fromAccount;
    }

    public AccountEntity findReceiverAccount(TransactionEntity transaction) throws NotFoundException{
        var toAccountOptional = accountRepository.findById(transaction.getToAccountNumber());
        if (toAccountOptional.isEmpty()) {
            throw new NotFoundException("Beneficiary account not found");
        }
        var toAccount = toAccountOptional.get();
        return toAccount;
    }

    public void checkFunds(AccountEntity fromAccount) throws UnauthorizedException{
        if (fromAccount.getAvailableBalance() < authorizationDto.getAmount()) {
            throw new UnauthorizedException("Authorization failed due to insufficient funds");
        }
    }

    //Need to double check this method or specifically doAuthorization method in Controller
    public AccountEntity findBeneficiaryAccount() throws NotFoundException{
        var toAccountOptional = accountRepository.findById(authorizationDto.getToAccountNumber());
        if (toAccountOptional.isEmpty()) {
            throw new NotFoundException("Beneficiary account not found");
        }
        var toAccount = toAccountOptional.get();
        return toAccount;
    }

    public void updateAvailableCustomerBalance(AccountEntity fromAccount){
        fromAccount.setAvailableBalance(fromAccount.getAvailableBalance()-authorizationDto.getAmount());
        accountRepository.save(fromAccount);
    }

    public void updateBeneficiaryAccountBalance(AccountEntity toAccount){
        toAccount.setAccountBalance(toAccount.getAccountBalance()+authorizationDto.getAmount());
        accountRepository.save(toAccount);
    }


}