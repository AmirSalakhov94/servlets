CREATE TABLE users
(
    id                  BIGSERIAL PRIMARY KEY,
    login               TEXT UNIQUE NOT NULL,
    password            TEXT        NOT NULL,
    type_restore_issue  TEXT        NOT NULL,
    value_restore_issue TEXT        NOT NULL,
    roles               TEXT[] NOT NULL DEFAULT '{ROLE_USER}',
    removed             BOOLEAN              DEFAULT FALSE,
    modified            TIMESTAMP,
    created             TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE users_password_restore
(
    login   TEXT      NOT NULL,
    key     TEXT PRIMARY KEY,
    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tokens
(
    user_id BIGINT    NOT NULL REFERENCES users,
    token   TEXT PRIMARY KEY,
    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

