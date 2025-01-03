package ec.com.sofka.mapper;

import ec.com.sofka.data.AccountEntity;
import ec.com.sofka.gateway.dto.AccountDTO;

public class AccountMapper {
    public static AccountEntity toEntity(AccountDTO accountDTO) {
        return new AccountEntity(accountDTO.getBalance(),
                accountDTO.getOwner(),
                accountDTO.getAccountNumber()
                );
    }

    public static AccountDTO toDTO(AccountEntity accountEntity) {
        return new AccountDTO(
                accountEntity.getBalance(),
                accountEntity.getAccountNumber(),
                accountEntity.getOwner()
        );
    }
}
