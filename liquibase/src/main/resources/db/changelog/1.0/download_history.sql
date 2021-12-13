create table if not exists download_history(
                                               id serial not null,
                                               primary key (id),
                                               file_name varchar (255) not null,
                                               date_create timestamp not null,
                                               ip_user varchar (50) not null,
                                               message_id int references messages(id)
);