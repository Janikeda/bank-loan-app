create table if not exists black_listed_person
(
    id        bigserial primary key,
    name      text   not null,
    last_name text   not null,
    inn       bigint not null
);

insert into black_listed_person (name, last_name, inn)
values ('Иван', 'Иванов', 3664069397),
       ('Петр', 'Петров', 7727563778),
       ('Семен', 'Семенов', 7714270020);
