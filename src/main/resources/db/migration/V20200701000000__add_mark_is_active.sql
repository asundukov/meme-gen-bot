ALTER TABLE mark add is_active BOOLEAN not null default true;
ALTER TABLE mark alter is_active drop default ;
