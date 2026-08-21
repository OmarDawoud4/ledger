package org.dawoud.ledger.account;

import org.springframework.transaction.annotation.Transactional;
// import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accounts ;
    public AccountService(AccountRepository accounts) {
        this.accounts = accounts;
    }

    @Transactional
    public Account create (String name , String currency){

        String normalized = currency.trim().toUpperCase();

        if (accounts.existsByNameAndCurrency(name , normalized)){
            throw new DuplicateAccountException(name , normalized);
            //rollback
        }
        return accounts.save(Account.open(name , normalized));
    }

    @Transactional(readOnly = true)
    public List<Account> findAll() {
        return accounts.findAll();

    }



}
