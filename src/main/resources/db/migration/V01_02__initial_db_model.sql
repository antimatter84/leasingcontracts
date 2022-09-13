create table if not exists vehicle
(
    vehicle_id bigint auto_increment,
    brand      varchar(255) not null,
    model      varchar(255) not null,
    model_year int          not null,
    vin        varchar(255),
    price      float        not null,

    primary key (vehicle_id)
);

create table if not exists customer
(
    customer_id   bigint auto_increment,
    firstname     varchar(255) not null,
    lastname      varchar(255) not null,
    date_of_birth DATE         not null,

    primary key (customer_id)
);

create table if not exists contract
(
    contract_id     bigint auto_increment,
    contract_number varchar(255),
    leasing_rate    float,
    customer_id     bigint,
    vehicle_id      bigint,

    primary key (contract_id),
    constraint unique_contract_number_constraint unique (contract_number),
    foreign key (customer_id)
        references customer (customer_id),
    foreign key (vehicle_id)
        references vehicle (vehicle_id)
);