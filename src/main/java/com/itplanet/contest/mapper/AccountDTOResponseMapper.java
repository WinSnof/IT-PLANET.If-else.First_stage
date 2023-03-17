package com.itplanet.contest.mapper;

import com.itplanet.contest.dto.AccountDTOResponse;
import com.itplanet.contest.entity.Account;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AccountDTOResponseMapper implements Function<Account, AccountDTOResponse> {

    @Override
    public AccountDTOResponse apply(Account account) {
        return new AccountDTOResponse(
                account.getId(),
                account.getFirstName(),
                account.getLastName(),
                account.getEmail()
        );
    }
}
