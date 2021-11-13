create table if not exists domainevententry
(
    globalindex         bigint       not null,
    eventidentifier     varchar(255) not null,
    metadata            bytea,
    payload             bytea        not null,
    payloadrevision     varchar(255),
    payloadtype         varchar(255) not null,
    timestamp           varchar(255) not null,
    aggregateidentifier varchar(255) not null,
    sequencenumber      bigint       not null,
    type                varchar(255),
    primary key (globalindex),
    unique (aggregateidentifier, sequencenumber),
    unique (eventidentifier)
);
