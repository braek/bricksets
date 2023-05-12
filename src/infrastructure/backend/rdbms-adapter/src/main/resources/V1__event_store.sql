CREATE TABLE event
(
    id          UUID      NOT NULL,
    position    BIGSERIAL NOT NULL,
    occurred_at TIMESTAMP NOT NULL,
    event_class VARCHAR   NOT NULL,
    event_value JSONB     NOT NULL,
    CONSTRAINT pk_event PRIMARY KEY (id),
    CONSTRAINT unique_position UNIQUE (position)
);
CREATE TABLE event_tag
(
    event_id  UUID    NOT NULL,
    tag_class VARCHAR NOT NULL,
    tag_value UUID    NOT NULL,
    CONSTRAINT pk_tag PRIMARY KEY (event_id, tag_class, tag_value),
    CONSTRAINT fk_tag_event FOREIGN KEY (event_id) REFERENCES event (id)
);