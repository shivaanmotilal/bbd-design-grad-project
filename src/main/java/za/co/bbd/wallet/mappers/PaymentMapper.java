package za.co.bbd.wallet.mappers;

import org.mapstruct.Mapper;
import za.co.bbd.wallet.dto.PaymentDto;
import za.co.bbd.wallet.model.PaymentModel;

@Mapper
public interface PaymentMapper {
    PaymentDto accountToDTO(PaymentModel accountModel);
}
