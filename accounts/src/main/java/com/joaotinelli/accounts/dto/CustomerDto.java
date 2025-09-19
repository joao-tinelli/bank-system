package com.joaotinelli.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    @NotEmpty(message = "Name can't be null or empty")
    @Size(min = 4, max = 30, message = "The length of the customer's name should be between 4 and 30")
    private String name;

    @NotEmpty(message = "Email can't be null or empty")
    @Email(message = "Email address must be a valid value")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    private AccountsDto accountsDto;
}
