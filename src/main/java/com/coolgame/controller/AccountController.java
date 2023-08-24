package com.coolgame.controller;

import com.coolgame.dto.AccountRequest;
import com.coolgame.model.Account;
import com.coolgame.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Endpoint to handle account creation
    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(@RequestBody AccountRequest accountRequest) {
        Account newAccount = accountService.createAccount(accountRequest);
        return ResponseEntity.ok(newAccount);
    }

    // Other endpoints related to accounts (update, retrieve, etc.)
}



