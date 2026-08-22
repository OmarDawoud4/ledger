package org.dawoud.ledger.journal;

import org.dawoud.ledger.account.Account;
import org.dawoud.ledger.account.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
public class LedgerBootstrap implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(LedgerBootstrap.class);

    static final String EQUITY = "equity";
    static final String TREASURY = "treasury";

    private static final long OPENING_CAPITAL_MINOR =1_000_000L;
    private final AccountRepository accounts;
    private final JournalEntryRepository entries;
    private final PostingRepository postings;
    public LedgerBootstrap(AccountRepository accounts, JournalEntryRepository entries, PostingRepository postings) {
        this.accounts = accounts;
        this.entries = entries;
        this.postings = postings;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        Account equity = ensureSystemAccount(EQUITY);
        Account treasury = ensureSystemAccount(TREASURY);
        seedOpeningCapital(equity, treasury);
    }
    private Account ensureSystemAccount(String name) {
        return accounts.findByName(name)
                .orElseGet(() -> accounts.save(Account.open(name, "USD")));
    }

    private void seedOpeningCapital(Account equity, Account treasury) {
        if (entries.count() > 0) {
            return;
        }
        JournalEntry entry = JournalEntry.createInternal();
        entries.save(entry);
        postings.saveAll(List.of(
                Posting.create(entry, equity, -OPENING_CAPITAL_MINOR),
                Posting.create(entry, treasury, OPENING_CAPITAL_MINOR)
        ));
        log.info("Ledger seeded: {} minor units of opening capital", OPENING_CAPITAL_MINOR);
    }

}
