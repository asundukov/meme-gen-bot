# meme-gen-bot service
Service can run multiple telegram bots, which place meme-gen-bots .

## Running
As a standart Spring Boot appliactoin

## Usage 
1. Create telegram bot  at https://t.me/BotFather.
1. Add bot via HTTP API or directly to db table bot (you need token bot)
1. Add meme images via HTTP API or direcly to db table + copy it to ./memes/<meme_id>.jpg
1. Place text-areas on your images
1. Use it in telegram according help-article direclty in bot chat or via inline mode

## Software dependencies
1. Java 11+
1. PostgreSQL 

# Java dependencies
1. Kotlin
1. Spring boot 2+
1. Flyway db

## Licence
Apache 2.0

## API
//TODO: place API examples

## Create db schema example

```
CREATE ROLE memegen PASSWORD 'memegen' LOGIN;
CREATE schema memegen;
GRANT ALL PRIVILEGES ON SCHEMA memegen TO memegen;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA memegen TO memegen;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA memegen TO memegen;
ALTER ROLE memgen SET search_path=memegen;
```
