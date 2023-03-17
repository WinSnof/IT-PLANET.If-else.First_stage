package com.itplanet.contest.service;

import com.itplanet.contest.dto.AccountDTORequest;
import com.itplanet.contest.dto.AccountDTOResponse;
import com.itplanet.contest.specification.AccountSearchSpec;

import java.util.List;

public interface IAccountService {
    void delete(Long accountId);

    AccountDTOResponse save(AccountDTORequest account);

    AccountDTOResponse update(AccountDTORequest account, Long accountId);

    List<AccountDTOResponse> search(AccountSearchSpec specification, Integer from, Integer size);

    AccountDTOResponse findById(Long accountId);
}
