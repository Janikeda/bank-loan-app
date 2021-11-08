create sequence if not exists hibernate_sequence start with 1 increment by 1;

create table if not exists association_value_entry
(
    id                bigint       not null,
    association_key   varchar(255) not null,
    association_value varchar(255),
    saga_id           varchar(255) not null,
    saga_type         varchar(255),
    primary key (id)
);

create table if not exists saga_entry
(
    saga_id         varchar(255) not null,
    revision        varchar(255),
    saga_type       varchar(255),
    serialized_saga bytea,
    primary key (saga_id)
);

create table if not exists tokenentry
(
    processorname varchar(255) not null,
    segment        integer      not null,
    owner          varchar(255),
    timestamp      varchar(255) not null,
    token          bytea,
    tokentype     varchar(255),
    primary key (processorname, segment)
);

create index if not exists saga_type_assoc_key_assoc_value_index on association_value_entry (saga_type, association_key, association_value);
create index if not exists saga_id_saga_type_index on association_value_entry (saga_id, saga_type);
