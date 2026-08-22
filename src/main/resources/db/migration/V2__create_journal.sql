CREATE TABLE journal_entries (
    id  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    -- idempotency key
    reference VARCHAR(100) UNIQUE ,
    created_at TIMESTAMPTZ  NOT NULL DEFAULT now()
);
CREATE TABLE postings (
    id  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    journal_entry_id UUID NOT NULL REFERENCES journal_entries(id),
    account_id UUID NOT NULL REFERENCES accounts(id),
    amount BIGINT NOT NULL ,
    CONSTRAINT ck_postings_amount_nonzero CHECK ( amount<> 0 )

);

CREATE INDEX ix_postings_account ON postings(account_id);