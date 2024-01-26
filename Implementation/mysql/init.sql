CREATE DATABASE IF NOT EXISTS tournament_schema;
USE tournament_schema;

create table IF NOT EXISTS tournament_schema.educator
(
    educator_id bigint not null
        primary key
);

create table IF NOT EXISTS tournament_schema.student
(
    student_id bigint not null
        primary key
);

create table IF NOT EXISTS tournament_schema.tournament
(
    tournament_id         bigint auto_increment
        primary key,
    registration_deadline varchar(255)                                        null,
    status                enum ('PREPARATION', 'ACTIVE', 'CLOSING', 'CLOSED') null,
    creator_id            bigint                                              not null,
    name                  varchar(30)                                         not null,
    description           varchar(255)                                        null
);

create table IF NOT EXISTS tournament_schema.score
(
    score_value   int    null,
    student_id    bigint not null,
    tournament_id bigint not null,
    primary key (student_id, tournament_id),
    constraint FKduewhdbwghcii0cx3012mxhdq
        foreign key (tournament_id) references tournament_schema.tournament (tournament_id),
    constraint FKnap51mbove93yjb09idc9jic6
        foreign key (student_id) references tournament_schema.student (student_id)
);

create table IF NOT EXISTS tournament_schema.student_participate_tournament
(
    student_id    bigint not null,
    tournament_id bigint not null,
    primary key (tournament_id, student_id),
    constraint student_fk
        foreign key (student_id) references tournament_schema.student (student_id),
    constraint student_participate_tournament_tournament_tournament_id_fk
        foreign key (tournament_id) references tournament_schema.tournament (tournament_id)
);

create index IF NOT EXISTS idx_registration_deadline
    on tournament_schema.tournament (registration_deadline);

create table IF NOT EXISTS tournament_schema.tournament_organizers
(
    tournament_id bigint not null,
    educator_id   bigint not null,
    constraint FKaeep7j652gbpwsqf0b5i8pq6
        foreign key (educator_id) references tournament_schema.educator (educator_id),
    constraint tournament_organizers_tournament_tournament_id_fk
        foreign key (tournament_id) references tournament_schema.tournament (tournament_id),
    constraint tournament_organizers_tournament_tournament_id_fk2
        foreign key (tournament_id) references tournament_schema.tournament (tournament_id)
);

create table IF NOT EXISTS tournament_schema.tournament_seq
(
    next_val bigint null
);

CREATE DATABASE IF NOT EXISTS battle_schema;
USE battle_schema;

CREATE DATABASE IF NOT EXISTS authentication;
USE authentication;

CREATE TABLE IF NOT EXISTS user
(
    user_id  BIGINT       NOT NULL,
    email    VARCHAR(255) NULL,
    password VARCHAR(255) NULL,
    name     VARCHAR(255) NULL,
    surname  VARCHAR(255) NULL,
    nickname VARCHAR(255) NULL,
    type     SMALLINT     NULL,
    CONSTRAINT pk_user PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS token
(
    id         BIGINT       NOT NULL,
    token      VARCHAR(255) NULL,
    token_type VARCHAR(255) NULL,
    revoked    BIT(1)       NOT NULL,
    expired    BIT(1)       NOT NULL,
    user_id    BIGINT       NULL,
    CONSTRAINT pk_token PRIMARY KEY (id)
);

ALTER TABLE token
    ADD CONSTRAINT uc_token_token UNIQUE (token);

ALTER TABLE token
    ADD CONSTRAINT FK_TOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES user (user_id);