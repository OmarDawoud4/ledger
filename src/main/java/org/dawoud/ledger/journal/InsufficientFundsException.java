package org.dawoud.ledger.journal;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException(java.util.UUID accountId, long requestedMinor) {
        super("insufficient funds in account " + accountId + ": requested " + requestedMinor);
    }
}
