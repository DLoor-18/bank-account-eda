package ec.com.sofka.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;

@Schema(description = "Object representing the data to create a account.")
public class AccountRequestDTO implements Serializable {

    @Schema(description = "Number of account", example = "2200000000")
    @NotNull(message = "number cannot be null")
    @Pattern(regexp = "^[0-9]+$", message = "Incorrect number format")
    @Size(min = 10, max = 10, message = "Incorrect number length")
    private String accountNumber;

    @Schema(description = "Balance of account", example = "200")
    @NotNull(message = "balance cannot be null")
    private BigDecimal balance;

    @Schema(description = "Status of account", example = "ACTIVE")
    @NotNull(message = "status cannot be null")
    @Pattern(regexp = "^(ACTIVE|INACTIVE)$", message = "Incorrect status")
    private String status;

    @Schema(description = "Account User ID")
    @NotNull(message = "userId cannot be null")
    private String userId;

    public AccountRequestDTO(String accountNumber, BigDecimal balance, String status, String userId) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
        this.userId = userId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}