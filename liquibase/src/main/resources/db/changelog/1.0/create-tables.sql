create table if not exists messages(
                                   id serial not null,
                                   primary key (id),
                                   title varchar(45) not null,
                                   size int not null default 0,
                                   date_create date not null ,
                                   author varchar(45) not null,
                                   content varchar(255) not null,
                                   type varchar(255) not null);