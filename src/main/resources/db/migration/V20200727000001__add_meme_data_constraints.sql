ALTER TABLE meme
    add constraint meme_size CHECK (width > 0 AND height > 0);
ALTER TABLE meme
    add constraint meme_thumb_size CHECK (thumb_width > 0 AND thumb_height > 0);

ALTER TABLE meme
    alter column width drop default,
    alter column height drop default,
    alter column thumb_width drop default,
    alter column thumb_height drop default;
