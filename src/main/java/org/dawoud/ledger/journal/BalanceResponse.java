package org.dawoud.ledger.journal;

import java.util.UUID;

public record BalanceResponse(UUID accountId , long balanceMinor) {
}
