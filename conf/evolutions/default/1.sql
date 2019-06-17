# COFFEE schema

# --- !Ups

CREATE TABLE USERS(
    ID varchar(255) NOT NULL,
    NAME varchar(255) NOT NULL,
    LASTNAME varchar(255) NOT NULL,
    EMAIL varchar(255) NOT NULL,
    PRIMARY KEY (ID)
);

# --- !Downs

DROP TABLE COFFEE;
