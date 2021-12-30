CREATE TABLE IF NOT EXISTS usersauth(
                                        id       serial,
                                        primary key (id),
                                        email varchar(255) not null,
                                        name     varchar(50)  not null,
                                        password varchar(255) not null,
                                        role     varchar(20)  not null ,
                                        status   varchar(20)  not null);
INSERT INTO usersauth(id,email,name,password,role,status) VALUES
(1,'user@yandex.ru','user','$2a$12$myLbVdvm2AyN5iCSpHYmwuUkJ2Lt65gcOytJQCC9V7yIw2qKcDcI6','USER','ACTIVE'),
(2,'moder@mail.ru','moder','$2a$12$rLUhrQRxjLCZynBTaaAQl.ebmkGl0mXmmn9Afv9hx9G/0vDnvkGZC','MODERATOR','ACTIVE'),
(3,'admin@gmail.com','admin','$2a$12$7qZv0zQ9Lqmhy76bjPl/CuvMwvlgJ8g68YYNwWqJMD4RuRvUw5XvG','ADMIN','ACTIVE');
