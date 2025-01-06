package ec.com.sofka.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TransactionResponseDTO implements Serializable {

    private String accountNumber;
    private BigDecimal amount;
    private AccountResponseDTO account;
    private TransactionTypeResponseDTO typeTransaction;

    public TransactionResponseDTO(String accountNumber, BigDecimal amount, AccountResponseDTO account, TransactionTypeResponseDTO typeTransaction) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.account = account;
        this.typeTransaction = typeTransaction;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public TransactionTypeResponseDTO getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(TransactionTypeResponseDTO typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public AccountResponseDTO getAccount() {
        return account;
    }

    public void setAccount(AccountResponseDTO account) {
        this.account = account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}