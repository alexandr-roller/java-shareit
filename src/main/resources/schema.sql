DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS item_requests;

CREATE TABLE IF NOT EXISTS users
(
    user_id identity,
    name    character varying(255) NOT NULL,
    email   character varying(64)  NOT NULL,
    login   character varying(225),
    PRIMARY KEY (user_id),
    UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS item_requests
(
    request_id   identity,
    description  text,
    requester_id bigint,
    create_date  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    PRIMARY KEY (request_id),
    CONSTRAINT item_requests_requester_id_fkey FOREIGN KEY (requester_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS items
(
    item_id      identity,
    name         character varying(255),
    description  text,
    is_available boolean,
    owner_id     bigint,
    request_id   bigint,
    PRIMARY KEY (item_id),
    CONSTRAINT items_owner_id_fkey FOREIGN KEY (owner_id) REFERENCES users (user_id),
    CONSTRAINT items_request_id_fkey FOREIGN KEY (request_id) REFERENCES item_requests (request_id)
);

CREATE TABLE IF NOT EXISTS bookings
(
    book_id    identity,
    start_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    end_date   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    item_id    bigint,
    booker_id  bigint,
    status     character varying(255),
    PRIMARY KEY (book_id),
    CONSTRAINT bookings_booker_id_fkey FOREIGN KEY (booker_id) REFERENCES users (user_id),
    CONSTRAINT bookings_item_id_fkey FOREIGN KEY (item_id) REFERENCES items (item_id)
);

CREATE TABLE IF NOT EXISTS comments
(
    comment_id  identity,
    text        text                        NOT NULL,
    item_id     bigint,
    author_id   bigint,
    create_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    PRIMARY KEY (comment_id),
    CONSTRAINT comments_author_id_fkey FOREIGN KEY (author_id) REFERENCES users (user_id),
    CONSTRAINT comments_item_id_fkey FOREIGN KEY (item_id) REFERENCES items (item_id)
);