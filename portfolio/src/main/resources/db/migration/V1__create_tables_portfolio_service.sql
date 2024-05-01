CREATE TABLE portfolio
(
    portfolio_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    ucc          UUID,
    CONSTRAINT pk_portfolio PRIMARY KEY (portfolio_id),
    CONSTRAINT uc_portfolio_ucc UNIQUE (ucc)
);


CREATE TABLE portfolio_symbol
(
    symbol_quantity_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    portfolio_id       BIGINT,
    symbol             VARCHAR(255),
    invested_amount    DOUBLE PRECISION,
    sold_amount        DOUBLE PRECISION,
    quantity           BIGINT,
    open_quantity      BIGINT,
    version            BIGINT                                  NOT NULL,
    CONSTRAINT pk_portfolio_symbol PRIMARY KEY (symbol_quantity_id),
    CONSTRAINT uc_4a3a8dbc826502f9edd639ab6 UNIQUE (portfolio_id, symbol),
    CONSTRAINT FK_PORTFOLIO_SYMBOL_ON_PORTFOLIO FOREIGN KEY (portfolio_id) REFERENCES portfolio (portfolio_id)
);


CREATE TABLE wallet
(
    wallet_id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    ucc               UUID,
    available_balance DOUBLE PRECISION DEFAULT 0,
    current_balance   DOUBLE PRECISION DEFAULT 0,
    version           BIGINT                                  NOT NULL,
    CONSTRAINT pk_wallet PRIMARY KEY (wallet_id),
    CONSTRAINT uc_wallet_ucc UNIQUE (ucc)
);


CREATE TABLE transaction
(
    id                      BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    wallet_transaction_type VARCHAR(255),
    amount                  DOUBLE PRECISION,
    wallet_id               BIGINT,
    timestamp               TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_transaction PRIMARY KEY (id),
    CONSTRAINT FK_TRANSACTION_ON_WALLET FOREIGN KEY (wallet_id) REFERENCES wallet (wallet_id)
);