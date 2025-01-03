package ec.com.sofka.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Document(collection = "bank_account")
public class AccountEntity {
    @Id
    private String id;

    @Field("account_number")
    private String accountNumber;

    @Field("account_holder")
    private String owner;

    @Field("global_balance")
    private BigDecimal balance;


    public AccountEntity(BigDecimal balance, String owner, String accountNumber) {
        this.balance = balance;
        this.owner = owner;
        this.accountNumber = accountNumber;
    }

    public String getId() {
        return id;
    }



    public String getAccountNumber() {
        return accountNumber;
    }


    public String getOwner() {
        return owner;
    }


    public BigDecimal getBalance() {
        return balance;
    }

}

