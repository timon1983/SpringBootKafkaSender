create table if not exists messages(
                                   id serial not null,
                                   primary key (id),
                                   title varchar(45) not null,
                                   size int not null default 0,
                                   date_create date not null ,
                                   time_create time not null,
                                   author varchar(45) not null,
                                   origin_file_name varchar(255) not null,
                                   file_name_for_s3 varchar(255) not null,
                                   type varchar(255) not null);