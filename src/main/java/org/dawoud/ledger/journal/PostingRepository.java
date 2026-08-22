package org.dawoud.ledger.journal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PostingRepository extends JpaRepository<Posting, UUID> {
    //Not stored
    @Query("select coalesce(sum(p.amount), 0) from Posting p where p.account.id = :accountId")
    long balanceOf (UUID accountId);
}
