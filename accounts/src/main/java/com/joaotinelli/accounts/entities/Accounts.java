package com.joaotinelli.accounts.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Accounts extends BaseEntity{

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "account_number")
    @Id
    // Strategy to generate the account_number is on AccountsService
    private Long accountNumber;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "branch_address")
    private String branchAddress;

    @Column(name = "communication_sw")
    private Boolean communicationSw;
}
