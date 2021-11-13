create table if not exists application_view (
    id varchar(130) primary key,
    name varchar(50) not null,
    last_name varchar(150) not null,
    age smallint not null,
    inn bigint not null,
    email varchar(150) not null,
    phone bigint not null,
    loan_amount_request bigint,
    created_date timestamp,
    status varchar(30),
    loan_amount_approved bigint
);
