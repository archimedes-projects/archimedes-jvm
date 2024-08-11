create table audit_log
(
    id           integer primary key generated always as identity,
    timestamp    timestamp      not null,
    elapsed_time bigint         not null,
    user_id      varchar(50)    not null,
    use_case     varchar(256)   not null,
    read_only    boolean        not null,
    success      boolean        not null,
    arguments    varchar(16384) not null,
    result       varchar(16384) not null
);
