package com.joaotinelli.accounts.service.implementation;

import com.joaotinelli.accounts.dto.*;
import com.joaotinelli.accounts.entities.Accounts;
import com.joaotinelli.accounts.entities.Customer;
import com.joaotinelli.accounts.exception.ResourceNotFoundException;
import com.joaotinelli.accounts.mapper.AccountsMapper;
import com.joaotinelli.accounts.mapper.CustomerMapper;
import com.joaotinelli.accounts.repository.AccountsRepository;
import com.joaotinelli.accounts.repository.CustomerRepository;
import com.joaotinelli.accounts.service.ICustomersService;
import com.joaotinelli.accounts.service.client.CardsFeignClient;
import com.joaotinelli.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());


        return customerDetailsDto;
    }
}
