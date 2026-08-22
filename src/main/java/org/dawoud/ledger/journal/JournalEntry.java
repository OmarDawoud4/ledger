package org.dawoud.ledger.journal;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "journal_entries")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class JournalEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id ;

    @Column(length = 100)
    private String reference ;

    @Column(name ="created_at", nullable = false, updatable = false)
    private Instant createdAt ;

    private JournalEntry (String reference){
        this.reference = reference;
    }

    public static JournalEntry create(String reference){
        return new JournalEntry(reference);
    }


    @PrePersist
    void onCreate (){
        createdAt = Instant.now();
    }
}
