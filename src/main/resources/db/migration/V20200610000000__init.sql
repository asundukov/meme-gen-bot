CREATE TABLE bot (
    bot_id serial not null primary key,
    admin_usr_id bigint not null,
    token varchar(1024) not null,
    total_generated INT not null,
    title varchar(32) not null,
    username varchar(32) not null,
    created_on timestamp not null default now()
);

CREATE UNIQUE index bot_token_idx  ON bot(token);
CREATE index bot_admin_usr_id_idx  ON bot(admin_usr_id);

CREATE TABLE meme (
    meme_id serial not null primary key ,
    bot_id int not null references bot(bot_id),
    title varchar(32) not null,
    description varchar(512) not null,
    total_generated INT not null,
    is_active BOOLEAN not null,
    created_on timestamp not null default now()
);

CREATE TABLE meme_text_area (
    meme_text_area_id serial not null primary key ,
    meme_id int not null references meme(meme_id),
    num int not null,
    top_pos decimal(7,4) not null,
    bottom_pos decimal(7,4) not null,
    let_pos decimal(7,4) not null,
    right_pos decimal(7,4) not null
);

CREATE TABLE usr (
    usr_id BIGINT NOT NULL PRIMARY KEY,
    first_name VARCHAR(1024),
    last_name VARCHAR(1024),
    user_name VARCHAR(1042),
    language_code VARCHAR(256),
    created_on TIMESTAMP NOT NULL
);

CREATE TABLE usr_bot_settings (
    usr_bot_settings_id SERIAL NOT NULL PRIMARY KEY ,
    usr_id BIGINT NOT NULL REFERENCES usr(usr_id),
    bot_id INT NOT NULL REFERENCES bot(bot_id),
    created_on TIMESTAMP NOT NULL,
    is_blocked BOOLEAN NOT NULL
);

CREATE UNIQUE INDEX usr_bot_settings_usr_id_bot_id_uniq ON usr_bot_settings(usr_id, bot_id);
