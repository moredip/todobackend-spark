create table todos (
    id serial primary key,
    title text not null,
    completed boolean not null,
    order int not null
);