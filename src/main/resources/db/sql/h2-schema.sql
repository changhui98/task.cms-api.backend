-- TODO schema
create table contents
(
    id                 bigint primary key      not null auto_increment,
    title              varchar(100)            not null,
    description        text,
    view_count         bigint                  not null default 0,
    created_date       timestamp default now() not null,
    created_by         varchar(50)             not null,
    last_modified_date timestamp,
    last_modified_by   varchar(50),
    deleted_date       timestamp,
    deleted_by         varchar(50)
);

create table content_views
(
    id                 bigint primary key auto_increment,
    content_id         bigint                  not null,
    username           varchar(50)             not null,
    viewed_at          timestamp               not null,
    constraint uq_content_views_content_user unique (content_id, username)
);

create table members
(
    id                 bigint primary key      not null auto_increment,
    username           varchar(50)             not null,
    password           varchar(255)            not null,
    role               varchar(20)             not null,
    created_date       timestamp default now() not null,
    last_modified_date timestamp                       ,
    deleted_date       timestamp
);

-- example
