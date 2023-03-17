package com.itplanet.contest.service.impl;

import com.itplanet.contest.dto.AccountDTORequest;
import com.itplanet.contest.dto.AccountDTOResponse;
import com.itplanet.contest.entity.Account;
import com.itplanet.contest.exception.BadRequestEx;
import com.itplanet.contest.exception.ConflictEx;
import com.itplanet.contest.exception.ForbiddenEx;
import com.itplanet.contest.exception.NotFoundEx;
import com.itplanet.contest.mapper.AccountDTORequestMapper;
import com.itplanet.contest.mapper.AccountDTOResponseMapper;
import com.itplanet.contest.repository.AccountRepo;
import com.itplanet.contest.repository.AnimalRepo;
import com.itplanet.contest.security.AccountDetails;
import com.itplanet.contest.service.IAccountService;
import com.itplanet.contest.specification.AccountSearchSpec;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountService implements IAccountService {

    private final AccountRepo accountRepo;
    private final AnimalRepo animalRepo;
    private final AccountDTOResponseMapper accountDTOMapper;
    private final AccountDTORequestMapper accountDTORequestMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void delete(Long accountId) {
        if(itsMyAccount(accountId)) {
            accountRepo.findById(accountId)
                    .orElseThrow(ForbiddenEx::new);
            if(!animalRepo.findByChipperIdId(accountId).isEmpty())
                throw new BadRequestEx();
            accountRepo.deleteById(accountId);
        } else throw new ForbiddenEx();
    }

    @Override
    public AccountDTOResponse save(AccountDTORequest account) {
        if(accountRepo.findByEmail(account.email()).isPresent())
            throw new ConflictEx(account.email());
        Account toSave = accountDTORequestMapper.apply(account);
        toSave.setPassword(passwordEncoder.encode(toSave.getPassword()));
        return accountDTOMapper.apply(accountRepo.save(toSave));
    }

    @Override
    public AccountDTOResponse update(AccountDTORequest account,
                                     Long accountId) {
        AccountDetails accountDetails =  (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(itsMyAccount(accountId)) {
            if(!accountDetails.getUsername().equals(account.email())) {
                if(accountRepo.findByEmail(account.email()).isPresent())
                    throw new ConflictEx(account.email());
            }
            Account toUpdate = accountDTORequestMapper.apply(account);
            toUpdate.setId(accountId);
            toUpdate.setPassword(passwordEncoder.encode(toUpdate.getPassword()));
            return accountDTOMapper.apply(accountRepo.save(toUpdate));
        }
        else throw new ForbiddenEx();
    }

    @Override
    public List<AccountDTOResponse> search(AccountSearchSpec specification,
                                           Integer from,
                                           Integer size) {
        return accountRepo
                .findAll(specification, Sort.by("id"))
                .stream()
                .skip(from)
                .limit(size)
                .map(accountDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDTOResponse findById(Long accountId) {
        return accountDTOMapper
                .apply(accountRepo
                .findById(accountId)
                .orElseThrow(() -> new NotFoundEx(accountId)));
    }

    private boolean itsMyAccount(Long id) {
        AccountDetails accountDetails =  (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountDetails.getId().equals(id);
    }
}
