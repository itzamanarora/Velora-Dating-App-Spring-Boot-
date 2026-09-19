CREATE TABLE otp_verifications
(
    id            UUID PRIMARY KEY,
    user_id       UUID                     NOT NULL,
    otp_code      VARCHAR(6)               NOT NULL,
    purpose       VARCHAR(30)              NOT NULL,
    expires_at    TIMESTAMP WITH TIME ZONE NOT NULL,
    is_used       BOOLEAN                  NOT NULL,
    attempt_count SMALLINT                 NOT NULL DEFAULT 0,
    created_at    TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT fk_otp_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE INDEX idx_otp_user_purpose ON otp_verifications (user_id, purpose, is_used);