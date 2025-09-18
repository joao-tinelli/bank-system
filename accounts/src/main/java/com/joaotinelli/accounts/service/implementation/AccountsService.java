package com.joaotinelli.accounts.service.implementation;

import com.joaotinelli.accounts.constants.AccountsConstants;
import com.joaotinelli.accounts.dto.AccountsDto;
import com.joaotinelli.accounts.dto.CustomerDto;
import com.joaotinelli.accounts.entities.Accounts;
import com.joaotinelli.accounts.entities.Customer;
import com.joaotinelli.accounts.exception.CustomerAlreadyExistsException;
import com.joaotinelli.accounts.exception.ResourceNotFoundException;
import com.joaotinelli.accounts.mapper.AccountsMapper;
import com.joaotinelli.accounts.mapper.CustomerMapper;
import com.joaotinelli.accounts.repository.AccountsRepository;
import com.joaotinelli.accounts.repository.CustomerRepository;
import com.joaotinelli.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsService implements IAccountsService {

    private final AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto){
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number "
                    +customerDto.getMobileNumber());
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Joao");

        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Joao");

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        return customerDto;

    }



}
