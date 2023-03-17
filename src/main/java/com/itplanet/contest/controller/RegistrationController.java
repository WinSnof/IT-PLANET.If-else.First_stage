package com.itplanet.contest.controller;

import com.itplanet.contest.dto.AccountDTORequest;
import com.itplanet.contest.dto.AccountDTOResponse;
import com.itplanet.contest.service.IAccountService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {

    private final IAccountService accountService;

    @PostMapping
    ResponseEntity<AccountDTOResponse> signIn(@RequestBody @Valid AccountDTORequest account) {
        return new ResponseEntity<>(accountService.save(account), HttpStatus.CREATED);
    }


}
