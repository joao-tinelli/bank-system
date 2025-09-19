package com.joaotinelli.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
public class AccountsDto {

    @Schema(
            description = "Account Number of JoaoBank account", example = "3454433243"
    )
    @NotEmpty(message = "Account number can't be null or empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Account number must be 10 digits")
    private Long accountNumber;

    @Schema(
            description = "Account type of JoaoBank account", example = "Savings"
    )
    @NotEmpty(message = "Account type can't be null or empty")
    private String accountType;

    @Schema(
            description = "JoaoBank branch address", example = "123 New York"
    )
    @NotEmpty(message = "Branch address can't be null or empty")
    private String branchAddress;
}
