CREATE TABLE accounts (
    account_id SERIAL PRIMARY KEY,
    pin VARCHAR(255) NOT NULL,
    balance NUMERIC(12,2) NOT NULL DEFAULT 0.00, CHECK (balance >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE transactions (
    transaction_id SERIAL PRIMARY KEY,
    account_id INT NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    amount NUMERIC(12,2) NOT NULL, CHECK (amount > 0),
    related_account_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (account_id)
        REFERENCES accounts(account_id),

    FOREIGN KEY (related_account_id)
        REFERENCES accounts(account_id)
);

SELECT *
FROM transactions;