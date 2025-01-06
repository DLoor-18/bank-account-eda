package ec.com.sofka.mapper;

import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.models.account.Account;
import ec.com.sofka.models.account.values.AccountId;
import ec.com.sofka.models.account.values.objects.AccountNumber;
import ec.com.sofka.responses.AccountResponse;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public static Account mapToModelFromDTO(AccountDTO account) {
        if (account == null) {
            return null;
        }

        return new Account(
                AccountId.of(account.getId()),
                AccountNumber.of(account.getNumber()),
                account.getBalance(),
                account.getStatus(),
                UserMapper.mapToModelFromDTO(account.getUser()));
    }

    public static AccountResponse mapToResponseFromModel(Account account) {
        if (account == null) {
            return null;
        }

        return new AccountResponse(
                account.getAccountNumber().value(),
                account.getBalance(),
                account.getStatus(),
                UserMapper.mapToResponseFromModel(account.getUser()));
    }

    public static AccountResponse mapToResponseFromDTO(AccountDTO account) {
        if (account == null) {
            return null;
        }

        return new AccountResponse(
                account.getNumber(),
                account.getBalance(),
                account.getStatus(),
                UserMapper.mapToResponseFromDTO(account.getUser()));
    }

    public static AccountDTO mapToDTOFromModel(Account account) {
        if (account == null) {
            return null;
        }

        return new AccountDTO(
                account.getAccountNumber().value(),
                account.getBalance(),
                account.getStatus(),
                account.getUser() != null ? UserMapper.mapToDTOFromModel(account.getUser()) : null);
    }

}