package com.itplanet.contest.controller;

import com.itplanet.contest.dto.AccountDTORequest;
import com.itplanet.contest.dto.AccountDTOResponse;
import com.itplanet.contest.service.IAccountService;
import com.itplanet.contest.specification.AccountSearchSpec;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@Validated
@AllArgsConstructor
public class AccountController {

    private final IAccountService accountService;

    @GetMapping("{accountId}")
    ResponseEntity<AccountDTOResponse> findById(@PathVariable("accountId") @Min(1) Long accountId) {
        return ResponseEntity.ok(accountService.findById(accountId));
    }

    @GetMapping("search")
    ResponseEntity<List<AccountDTOResponse>> search(AccountSearchSpec specification,
                                                    @RequestParam(name = "from", defaultValue = "0") @Min(0) Integer from,
                                                    @RequestParam(name = "size", defaultValue = "10") @Min(1) Integer size) {
        return ResponseEntity.ok(accountService.search(specification, from, size));
    }

    @PutMapping("{accountId}")
    ResponseEntity<AccountDTOResponse> update(@RequestBody @Valid AccountDTORequest account, @PathVariable("accountId") @Min(1) Long accountId) {
        return ResponseEntity.ok(accountService.update(account, accountId));
    }

    @DeleteMapping("{accountId}")
    void delete(@PathVariable("accountId") @Min(1) Long accountId) {
        accountService.delete(accountId);
    }
}
