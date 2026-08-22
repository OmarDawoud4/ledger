package org.dawoud.ledger.journal;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dawoud.ledger.account.Account;

import java.util.UUID;

@Entity
@Table(name="postings")
@Getter // immutable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of="id")
public class Posting {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "journal_entry_id", nullable = false)
    private JournalEntry journalEntry;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    private long amount;

    private Posting(JournalEntry journalEntry, Account account, long amount) {
        this.journalEntry = journalEntry;
        this.account = account;
        this.amount = amount;
    }

    public static Posting create(JournalEntry entry, Account account, long amount) {
        if (amount == 0) {
            throw new IllegalArgumentException("posting amount must not be zero");
        }
        return new Posting(entry, account, amount);

    }
}