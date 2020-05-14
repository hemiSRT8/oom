DROP TABLE IF EXISTS player;
DROP TABLE IF EXISTS player_backup;

CREATE TABLE player (
    id bigserial primary key,
    nick varchar(20) NOT NULL,
    updated timestamp NOT NULL
);

CREATE TABLE player_backup (
    id bigint NOT NULL,
    nick varchar(20) NOT NULL,
    updated timestamp NOT NULL
);
