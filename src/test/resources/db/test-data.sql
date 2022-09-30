INSERT INTO customer (firstname, lastname, date_of_birth)
VALUES ('Arnold', 'Schwarzenegger', '1947-7-30'),
       ('Keanu', 'Reeves', '1964-9-2');

INSERT INTO vehicle (brand, model, model_year, vin, price)
VALUES ('Mercedes', 'A180', 2016, 'M123456', 34000),
       ('Audi', 'A3', 2012, 'A123123', 22000);

INSERT INTO contract (contract_number, leasing_rate, customer_id, vehicle_id)
VALUES ('LC0001', 230,
        (SELECT customer_id FROM customer WHERE lastname = 'Schwarzenegger' LIMIT 1),
        (SELECT vehicle_id FROM vehicle WHERE model = 'A180' LIMIT 1));