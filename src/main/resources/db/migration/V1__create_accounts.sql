CREATE TABLE accounts (
    id         UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    name       VARCHAR(100) NOT NULL,
    currency   CHAR(3)      NOT NULL,
    status     VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT ck_accounts_status CHECK (status IN ('ACTIVE', 'FROZEN', 'CLOSED')),
    CONSTRAINT ck_accounts_currency CHECK (currency ~ '^[A-Z]{3}$')
);

CREATE UNIQUE INDEX ux_accounts_name_currency ON accounts (name, currency);
