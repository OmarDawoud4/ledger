package org.dawoud.ledger.journal;

public class DuplicateReferenceException extends RuntimeException {
    public DuplicateReferenceException(String reference) {
        super("another request with reference already succeeded or is in flight: " + reference);
    }

}
