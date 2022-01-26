
[![Build Status](https://app.travis-ci.com/timon1983/SpringBootKafkaSender.svg?branch=master)](https://app.travis-ci.com/timon1983/SpringBootKafkaSender)

# SpringBootKafkaSender

Multimodule project for do operation with files

liquibase - модуль системы управления версиями базы данных;

sbks - модуль backend взаимодействия с БД и с файловым хранилищем AWS S3;

uisbks - модуль frontend;

kafka - модуль для отправки сообщений в kafka

Security с помощью Jwt токена, три роли:
ADMIN (логин admin@gmail.com, пароль admin),
MODERATOR (логин moder@mail.ru, пароль moder),
USER .

При регистрации нового пользователя ему присуждается роль USER

