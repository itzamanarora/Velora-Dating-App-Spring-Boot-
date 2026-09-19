CREATE TABLE roles
(
    id           UUID PRIMARY KEY,
    name         VARCHAR(75)              NOT NULL UNIQUE,
    display_name VARCHAR(75)              NOT NULL UNIQUE,
    is_active    BOOLEAN                  NOT NULL DEFAULT TRUE,
    created_at   TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at   TIMESTAMP WITH TIME ZONE,

    CONSTRAINT uk_role_name UNIQUE (name),
    CONSTRAINT uk_role_display_name UNIQUE (display_name)
);