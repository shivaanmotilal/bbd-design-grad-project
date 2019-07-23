package za.co.bbd.wallet.mappers;

import org.mapstruct.Mapper;
import za.co.bbd.wallet.dto.TransactionDto;
import za.co.bbd.wallet.model.TransactionModel;

@Mapper
public interface TransactionMapper {
    TransactionDto accountToDTO(TransactionModel accountModel);
}
