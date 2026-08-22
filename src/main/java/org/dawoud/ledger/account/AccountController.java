package org.dawoud.ledger.account;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest request){

        Account account = accountService.create(request.name(),request.currency());

        return ResponseEntity.status(HttpStatus.CREATED).body(AccountResponse.from(account));
    }


    @GetMapping
    public List<AccountResponse> list(){

        return accountService.findAll().stream().map(AccountResponse::from).toList();
    }
}
