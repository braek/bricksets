CREATE TABLE event
(
    id          UUID      NOT NULL,
    position    BIGSERIAL NOT NULL,
    occurred_on TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    clazz       VARCHAR   NOT NULL,
    content     JSONB     NOT NULL,
    CONSTRAINT pk_event PRIMARY KEY (id),
    CONSTRAINT uc_position UNIQUE (position)
);
CREATE TABLE tag
(
    event_id UUID    NOT NULL,
    clazz    VARCHAR NOT NULL,
    value    VARCHAR NOT NULL,
    CONSTRAINT pk_tag PRIMARY KEY (event_id, clazz, value),
    CONSTRAINT fk_tag_event FOREIGN KEY (event_id) REFERENCES event (id)
);