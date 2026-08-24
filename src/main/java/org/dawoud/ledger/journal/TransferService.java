package org.dawoud.ledger.journal;

import org.dawoud.ledger.account.Account;
import org.dawoud.ledger.account.AccountRepository;
import org.dawoud.ledger.account.AccountStatus;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TransferService {

    private final AccountRepository accounts;
    private final JournalEntryRepository entries ;
    private final PostingRepository postings;
    public TransferService(AccountRepository accounts, JournalEntryRepository entries, PostingRepository postings) {
        this.accounts = accounts;
        this.entries = entries;
        this.postings = postings;
    }

    @Transactional
    public JournalEntry transfer(String reference , UUID fromAccountId, UUID toAccountId, long amountMinor) {
        var replay = entries.findByReference(reference);
        if (replay.isPresent()) {
            return replay.get();
        }


        validateInput(fromAccountId, toAccountId, amountMinor);
        Account from = load(fromAccountId);
        Account to = load(toAccountId);
        validateAccounts(from , to );
        if (postings.balanceOf(fromAccountId) < amountMinor) {
            throw new InsufficientFundsException(fromAccountId, amountMinor);
        }

        JournalEntry entry = JournalEntry.create(reference);

        List<Posting> legs = List.of(
                Posting.create(entry,from, -amountMinor),
                Posting.create(entry,to, amountMinor)
        );

        assertBalanced(legs);
        try {
            entries.saveAndFlush(entry);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateReferenceException(reference);
        }


        postings.saveAll(legs);
        return  entry;

    }

    private void validateInput(UUID from , UUID to , long amountMinor) {
        if (amountMinor <= 0){
            throw new InvalidTransferException("Amount must be greater than zero");
        }
        if (from.equals(to)) {
            throw new InvalidTransferException("Source and Target must differ");
        }


    }
    private Account load(UUID id) {
        return accounts.findByIdForUpdate(id).orElseThrow(() -> new AccountNotFoundException(id));
    }


    private void validateAccounts(Account from, Account to) {
        if (!from.getCurrency().equals(to.getCurrency())) {
            throw new InvalidTransferException("currency mismatch: "
                    + from.getCurrency() + " -> " + to.getCurrency());
        }
        if (from.getStatus() != AccountStatus.ACTIVE
                || to.getStatus() != AccountStatus.ACTIVE) {
            throw new InvalidTransferException("frozen or closed account in transfer");
        }
    }

    private void assertBalanced(List<Posting> legs) {
        long sum = legs.stream().mapToLong(Posting::getAmount).sum();
        if (sum != 0) {
            throw new IllegalStateException("journal entry not balanced: sum=" + sum);
        }
    }


    @Transactional(readOnly = true)
    public long balanceOf(UUID accountId) {
        accounts.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
        return postings.balanceOf(accountId);
    }

}

