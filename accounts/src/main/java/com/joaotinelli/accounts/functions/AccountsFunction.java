package com.joaotinelli.accounts.functions;

import com.joaotinelli.accounts.service.IAccountsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class AccountsFunction {

    private static final Logger log = LoggerFactory.getLogger(AccountsFunction.class);

    // Customer: Function that only receives and input
    // It's responsible to accept the message from the message microsevice
    @Bean
    public Consumer<Long> updateCommunication(IAccountsService accountsService){
        return accountNumber -> {
            log.info("Updating communication status for the account number:" + accountNumber.toString());
            accountsService.updateCommunicationStatus(accountNumber);
        };
    }
}
