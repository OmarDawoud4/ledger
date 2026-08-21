package org.dawoud.ledger.account;

import java.time.Instant;
import java.util.UUID;

public record AccountResponse(

        UUID id ,
        String name ,
        String currency ,
        AccountStatus status ,
        Instant createdAt
) {

    public static AccountResponse from (Account account) {

        return new AccountResponse(
                account.getId(),
                account.getName(),
                account.getCurrency(),
                account.getStatus(),
                account.getCreatedAt()
        );
    }
}
