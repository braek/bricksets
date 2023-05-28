CREATE TABLE event
(
    id          UUID      NOT NULL,
    position    BIGSERIAL NOT NULL,
    occurred_on TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    event_class VARCHAR   NOT NULL,
    event_value JSONB     NOT NULL,
    CONSTRAINT pk_event PRIMARY KEY (id),
    CONSTRAINT uc_position UNIQUE (position)
);
CREATE TABLE tag
(
    event_id  UUID    NOT NULL,
    tag_class VARCHAR NOT NULL,
    tag_value VARCHAR NOT NULL,
    CONSTRAINT pk_tag PRIMARY KEY (event_id, tag_class, tag_value),
    CONSTRAINT fk_tag_event FOREIGN KEY (event_id) REFERENCES event (id)
);