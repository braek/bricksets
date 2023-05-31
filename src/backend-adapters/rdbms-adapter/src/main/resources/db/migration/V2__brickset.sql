CREATE TABLE brickset_view
(
    id          UUID    NOT NULL,
    number      VARCHAR NOT NULL,
    title       VARCHAR NOT NULL,
    created_on  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    modified_on TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_brickset PRIMARY KEY (id),
    CONSTRAINT unique_number UNIQUE (number)
);