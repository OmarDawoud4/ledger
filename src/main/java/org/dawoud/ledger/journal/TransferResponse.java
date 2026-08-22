package org.dawoud.ledger.journal;

import java.util.UUID;

public record TransferResponse(UUID journalEntryId) {
    public static TransferResponse from(JournalEntry journalEntry) {
        return new TransferResponse(journalEntry.getId());
    }
}
