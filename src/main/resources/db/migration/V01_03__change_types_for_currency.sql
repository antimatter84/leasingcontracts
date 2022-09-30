ALTER TABLE vehicle
    MODIFY COLUMN price decimal(15, 4);

ALTER TABLE contract
    MODIFY COLUMN leasing_rate decimal(15, 4) not null;