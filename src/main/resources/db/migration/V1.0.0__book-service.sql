create table Book (
    id serial primary key,
    author varchar(255),
    creationDate timestamp(6) with time zone,
    genre varchar(255),
    isbn13 varchar(255),
    title varchar(255),
    yearOfPublication integer not null
);

create index isbn13_index
    on Book (isbn13);
