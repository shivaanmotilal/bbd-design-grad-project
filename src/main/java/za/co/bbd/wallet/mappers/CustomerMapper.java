package za.co.bbd.wallet.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import za.co.bbd.wallet.dto.CustomerDto;
import za.co.bbd.wallet.model.CustomerModel;

@Mapper
public interface CustomerMapper {
//    @Mapping(target="", source="");
    CustomerDto customerToDTO(CustomerModel customerModel);

}
