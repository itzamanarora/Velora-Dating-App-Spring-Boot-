CREATE TABLE swipes
(
    id         UUID PRIMARY KEY,
    swiper_id  UUID                     NOT NULL REFERENCES profiles (id) ON DELETE CASCADE,
    swipee_id  UUID                     NOT NULL REFERENCES profiles (id) ON DELETE CASCADE,
    swipe_type VARCHAR(20)              NOT NULL CHECK (swipe_type IN ('LIKE', 'DISLIKE')),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT uk_swipes_swiper_swipee UNIQUE (swiper_id, swipee_id)
);