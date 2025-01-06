package ec.com.sofka.requests;

import ec.com.sofka.generics.shared.Request;
import ec.com.sofka.utils.enums.StatusEnum;

import java.math.BigDecimal;

public class AccountRequest extends Request {
    private String accountNumber;
    private BigDecimal balance;
    private StatusEnum status;
    private String userId;

    public AccountRequest(String accountNumber, BigDecimal balance, StatusEnum status, String userId) {
        super(null);
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

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}