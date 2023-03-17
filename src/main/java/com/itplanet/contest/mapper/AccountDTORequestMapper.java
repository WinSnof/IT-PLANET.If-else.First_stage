package com.itplanet.contest.mapper;

import com.itplanet.contest.dto.AccountDTORequest;
import com.itplanet.contest.entity.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class AccountDTORequestMapper implements Function<AccountDTORequest, Account> {


    @Override
    public Account apply(AccountDTORequest accountDTORequest) {
        return new Account(
                accountDTORequest.firstName(),
                accountDTORequest.lastName(),
                accountDTORequest.email(),
                accountDTORequest.password()
        );
    }
}
