package org.dawoud.ledger.journal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record TransferRequest(

        @NotNull
        UUID fromAccountId ,
        @NotNull
        UUID toAccountId ,
        @Positive
        long amountMinor,
        @NotBlank
        String reference

) {
}
