package org.dawoud.ledger.account;

public class DuplicateAccountException extends RuntimeException {

    public DuplicateAccountException(String name , String currency ) {

        super("Account with name " + name + " already exists in account " + currency);
    }
}
