package com.joaotinelli.accounts.service;

import com.joaotinelli.accounts.dto.CustomerDto;

public interface IAccountsService {

    void createAccount(CustomerDto customerDto);

    CustomerDto fetchAccount(String mobileNumber);
}
