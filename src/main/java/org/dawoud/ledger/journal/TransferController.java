package org.dawoud.ledger.journal;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class TransferController {

    private final TransferService service;
    public TransferController(TransferService transferService) {
        this.service = transferService;
    }

    @PostMapping("/transfers")
    public ResponseEntity<TransferResponse> transfer(@Valid @RequestBody TransferRequest transferRequest) {
        JournalEntry entry = service.transfer(
                transferRequest.reference(),
                transferRequest.fromAccountId(),
                transferRequest.toAccountId(),
                transferRequest.amountMinor()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(TransferResponse.from(entry));    }


    @GetMapping("/accounts/{accountId}/balance")
    public BalanceResponse balance(@PathVariable UUID accountId) {
        return new BalanceResponse(accountId, service.balanceOf(accountId));
    }


}
