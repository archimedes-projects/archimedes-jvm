CREATE FUNCTION archimedes_now_utc() RETURNS timestamp AS
$$
BEGIN
    RETURN NOW() AT TIME ZONE 'utc';
END;
$$ LANGUAGE plpgsql;

CREATE FUNCTION archimedes_audit_set_updated_at() RETURNS trigger AS
$$
BEGIN
    new.audit_updated_at = archimedes_now_utc();
    RETURN new;
END;
$$ LANGUAGE plpgsql;


-- -------------------------------------------------------------------------------------------------------
-- Security authentication and authorization
--

CREATE TABLE archimedes_security_role
(
    name             text PRIMARY KEY,

    audit_created_at timestamp NOT NULL DEFAULT archimedes_now_utc(),
    audit_updated_at timestamp NOT NULL DEFAULT archimedes_now_utc()
);

CREATE TRIGGER archimedes_security_role_audit
    BEFORE UPDATE
    ON archimedes_security_role
    FOR EACH ROW
EXECUTE PROCEDURE archimedes_audit_set_updated_at();


CREATE TABLE archimedes_security_subject
(
    id               integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    principal_name   text      NOT NULL UNIQUE,
    attributes       text      NOT NULL,

    audit_created_at timestamp NOT NULL DEFAULT archimedes_now_utc(),
    audit_updated_at timestamp NOT NULL DEFAULT archimedes_now_utc()
);

CREATE TRIGGER archimedes_security_subject_audit
    BEFORE UPDATE
    ON archimedes_security_subject
    FOR EACH ROW
EXECUTE PROCEDURE archimedes_audit_set_updated_at();


CREATE TABLE archimedes_security_subject_role_relation
(
    subject_id       integer   NOT NULL REFERENCES archimedes_security_subject (id),
    role_name        text      NOT NULL REFERENCES archimedes_security_role (name),

    PRIMARY KEY (subject_id, role_name),

    audit_created_at timestamp NOT NULL DEFAULT archimedes_now_utc(),
    audit_updated_at timestamp NOT NULL DEFAULT archimedes_now_utc()
);

CREATE INDEX ON archimedes_security_subject_role_relation (subject_id);

CREATE TRIGGER archimedes_security_subject_role_relation_audit
    BEFORE UPDATE
    ON archimedes_security_subject_role_relation
    FOR EACH ROW
EXECUTE PROCEDURE archimedes_audit_set_updated_at();


CREATE TABLE archimedes_security_password
(
    subject_id       integer PRIMARY KEY REFERENCES archimedes_security_subject (id),
    secret           text      NOT NULL,

    audit_created_at timestamp NOT NULL DEFAULT archimedes_now_utc(),
    audit_updated_at timestamp NOT NULL DEFAULT archimedes_now_utc()
);

CREATE TRIGGER archimedes_security_password_audit
    BEFORE UPDATE
    ON archimedes_security_password
    FOR EACH ROW
EXECUTE PROCEDURE archimedes_audit_set_updated_at();


CREATE TABLE archimedes_security_refresh_token
(
    refresh_token    text PRIMARY KEY,
    principal_name   text      NOT NULL UNIQUE REFERENCES archimedes_security_subject (principal_name),

    audit_created_at timestamp NOT NULL DEFAULT archimedes_now_utc(),
    audit_updated_at timestamp NOT NULL DEFAULT archimedes_now_utc()
);

CREATE TRIGGER archimedes_security_refresh_token_audit
    BEFORE UPDATE
    ON archimedes_security_refresh_token
    FOR EACH ROW
EXECUTE PROCEDURE archimedes_audit_set_updated_at();
