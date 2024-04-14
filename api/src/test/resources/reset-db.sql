drop table if exists users;

delete
from flyway_schema_history
where installed_rank > 1;