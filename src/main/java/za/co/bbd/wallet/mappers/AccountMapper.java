package za.co.bbd.wallet.mappers;

import org.mapstruct.Mapper;
import za.co.bbd.wallet.dto.AccountDto;
import za.co.bbd.wallet.model.AccountModel;

@Mapper
public interface AccountMapper {
    AccountDto accountToDTO(AccountModel accountModel);
}
