create table if not exists messages_deleted(
                                              id serial not null,
                                              primary key (id),
                                              title varchar(45) not null,
                                              size int not null default 0,
                                              date_delete date not null ,
                                              time_delete time not null,
                                              author varchar(45) not null,
                                              origin_file_name varchar(255) not null,
                                              type varchar(255) not null);