CREATE TABLE users
(
    id         UUID PRIMARY KEY,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    role_id    UUID         NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE,
    updated_at TIMESTAMP WITH TIME ZONE,

    CONSTRAINT uk_user_email UNIQUE (email),
    CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES roles (id)
)