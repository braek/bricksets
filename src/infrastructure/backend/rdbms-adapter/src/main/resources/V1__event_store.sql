CREATE TABLE event
(
    id          UUID        NOT NULL,
    timestamp   TIMESTAMPTZ NOT NULL,
    position    SERIAL      NOT NULL,
    event_class VARCHAR     NOT NULL,
    event_value JSONB       NOT NULL,
    CONSTRAINT pk_event PRIMARY KEY (id)
);
CREATE TABLE tag
(
    event_id  UUID    NOT NULL,
    tag_class VARCHAR NOT NULL,
    tag_value UUID    NOT NULL,
    CONSTRAINT pk_tag PRIMARY KEY (event_id, tag_class, tag_value),
    CONSTRAINT fk_tag_event FOREIGN KEY (event_id) REFERENCES event (id)
);