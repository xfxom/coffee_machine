CREATE TABLE beverage_history (
    id SERIAL PRIMARY KEY,
    drink_id INT NOT NULL,
    prepared_at TIMESTAMPTZ DEFAULT NOW(),

    CONSTRAINT fk_drink
        FOREIGN KEY (drink_id)
        REFERENCES recipes(id)
        ON DELETE RESTRICT
);
