
drop table if exists images;
drop table if exists reviews;
drop table if exists payments;
drop table if exists favorites;
drop table if exists lodgings;
drop table if exists bookings;
drop table if exists properties;
drop table if exists coworking_spaces;
drop table if exists tourist_spots;
drop table if exists places;
drop table if exists activity;
drop table if exists users;

delete
from flyway_schema_history
where installed_rank > 1;