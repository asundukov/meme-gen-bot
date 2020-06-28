# mark-on-image-bot service
Service can run multiple telegram bots, which place administrator's mark on user's images.

## What it can do?
1. Receive user's image and place campaign mark or watermark and send it back to user. See example at https://t.me/RickAndMortyPicBot
1. User can choose mark for all next downloads.
1. Administrator can manage bots via HTTP JSON API. Example of manage bot see at https://t.me/MarksManageBot
1. For each mark administrator can choose align (top-left, top-right, bottom-left, bottom-right), opacity, size.
1. Supported types: JPG, BMP, PNG, GIF

## Running
As a standart Spring Boot appliactoin

## Usage 
1. Create telegram bot  at https://t.me/BotFather.
1. Add bot via HTTP API or directly to db table bot (you need token bot)
1. Add mark or watermark via HTTP API or direcly to db table + copy it to ./marks/<mark_id>.png
1. Use it in telegram and send links to your audience, team or funs

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
CREATE ROLE markonimage PASSWORD 'markonimage' LOGIN;
CREATE schema markonimage;
GRANT ALL PRIVILEGES ON SCHEMA markonimage TO markonimage;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA markonimage TO markonimage;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA markonimage TO markonimage;
ALTER ROLE markonimage SET search_path=markonimage;
```