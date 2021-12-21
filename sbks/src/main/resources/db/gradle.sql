create table if not exists docs(
                                   doc_id serial not null,
                                   primary key (doc_id),
                                   name varchar(255) not null,
                                   code int not null);