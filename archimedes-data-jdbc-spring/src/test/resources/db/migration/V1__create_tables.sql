create table spring_foo_versioned_entity
(
    id            int,
    version_start timestamp not null,
    version_end   timestamp,
    data          int
);
