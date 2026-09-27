CREATE TABLE profiles
(
    id                  UUID PRIMARY KEY         NOT NULL,
    user_id             UUID                     NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    first_name          VARCHAR(255)             NOT NULL,
    last_name           VARCHAR(255)             NOT NULL,
    date_of_birth       DATE                     NOT NULL,
    gender              VARCHAR(50)              NOT NULL,
    profile_picture_url VARCHAR(2048),
    created_at          TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at          TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT uk_profiles_user_id UNIQUE (user_id)
);