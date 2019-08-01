package za.co.bbd.wallet.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import za.co.bbd.wallet.dto.AuthorizationDto;
import za.co.bbd.wallet.entity.CustomerEntity;
import za.co.bbd.wallet.exceptions.ForbiddenException;
import za.co.bbd.wallet.exceptions.NotFoundException;
import za.co.bbd.wallet.exceptions.UnauthorizedException;
import za.co.bbd.wallet.repository.CustomerRepository;

@Service("wallet.AuthorizationService")
public class AuthorizationService {
    Logger LOGGER = LoggerFactory.getLogger(AuthorizationService.class);
    AuthorizationDto authorizationDto;
    CustomerRepository customerRepository;

    @Autowired
    private AuthorizationService(CustomerRepository customerRepository, @Qualifier("wallet.AuthorizationDto") AuthorizationDto authorizationDto){
        this.customerRepository= customerRepository;
        this.authorizationDto= authorizationDto;
    }

    public CustomerEntity checkAuthorisation(String customerId, String password) throws ForbiddenException,NotFoundException,UnauthorizedException {
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
        return customer;
    }
}
