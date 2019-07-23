package za.co.bbd.wallet.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.bbd.wallet.dto.CustomerDto;
import za.co.bbd.wallet.dto.NewCustomerDto;
import za.co.bbd.wallet.entity.CustomerEntity;
import za.co.bbd.wallet.exceptions.BadRequestException;
import za.co.bbd.wallet.exceptions.ForbiddenException;
import za.co.bbd.wallet.exceptions.NotFoundException;
import za.co.bbd.wallet.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@Service("wallet.CustomerService")
public class CustomerService {
    private String customerId;
    Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);
    private CustomerRepository customerRepository;

    public CustomerEntity findCustomer(String customerId, String password) throws ForbiddenException, NotFoundException{
        // customer findCustomer(customerId)
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
        return customer;
    }

    public void checkPassword(NewCustomerDto newCustomerDto) throws BadRequestException{
        if (!newCustomerDto.getPassword().equals(newCustomerDto.getConfirmedPassword())) {
            LOGGER.info("PASSWORDS DO NOT MATCH");
            throw new BadRequestException("Passwords do not match");
        }
    }

    public String createCustomerUserId(){
        Random rand = new Random();
        String userId = "5" + String.format("%07d", rand.nextInt(1000000));

        Optional<CustomerEntity> customerOptional = customerRepository.findById(userId);
        while (customerOptional.isPresent()) {
            userId = "5" + String.format("%07d", rand.nextInt(1000000));
            customerOptional = customerRepository.findById(userId);
        }
        return userId;
    }

    public CustomerDto saveCustomer(NewCustomerDto newCustomerDto, String userId){
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

}
