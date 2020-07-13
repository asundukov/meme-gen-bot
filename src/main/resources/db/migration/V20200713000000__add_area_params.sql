ALTER TABLE meme_text_area
    add column text_color_red smallint NOT NULL DEFAULT 0 CONSTRAINT check_text_red CHECK (text_color_red >= 0 AND text_color_red <= 255);
ALTER TABLE meme_text_area
    add column text_color_green smallint NOT NULL DEFAULT 0 CHECK (text_color_green >= 0 AND text_color_green <= 255);
ALTER TABLE meme_text_area
    add column text_color_blue smallint NOT NULL DEFAULT 0 CHECK (text_color_blue >= 0 AND text_color_blue <= 255);
ALTER TABLE meme_text_area
    add column text_color_alpha smallint NOT NULL DEFAULT 255 CHECK (text_color_alpha >= 0 AND text_color_alpha <= 255);

ALTER TABLE meme_text_area
    add column bg_color_red smallint NOT NULL DEFAULT 255 CHECK (bg_color_red >= 0 AND bg_color_red <= 255);
ALTER TABLE meme_text_area
    add column bg_color_green smallint NOT NULL DEFAULT 255 CHECK (bg_color_green >= 0 AND bg_color_green <= 255);
ALTER TABLE meme_text_area
    add column bg_color_blue smallint NOT NULL DEFAULT 255 CHECK (bg_color_blue >= 0 AND bg_color_blue <= 255);
ALTER TABLE meme_text_area
    add column bg_color_alpha smallint NOT NULL DEFAULT 255 CHECK (bg_color_alpha >= 0 AND bg_color_alpha <= 255);

ALTER TABLE meme_text_area
    alter column text_color_red DROP DEFAULT,
    alter column text_color_green DROP DEFAULT,
    alter column text_color_blue DROP DEFAULT,
    alter column text_color_alpha DROP DEFAULT;

ALTER TABLE meme_text_area
    alter column bg_color_red DROP DEFAULT,
    alter column bg_color_green DROP DEFAULT,
    alter column bg_color_blue DROP DEFAULT,
    alter column bg_color_alpha DROP DEFAULT;
