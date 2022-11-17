DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '1999-01-08 04:05', 'Завтрак', 400),
       (100000, '1999-01-08 20:05', 'Ужин', 300),
       (100000, '2003-05-13 11:05', 'Завтрак', 700),
       (100000, '2003-05-24 19:15', 'Ужин', 300),
       (100000, '2003-05-24 21:15', 'Ужин2', 900),
       (100000, '2003-05-27 12:35', 'Обед', 360),
       (100000, '2003-05-28 14:15', 'Обед', 930),
       (100000, '2003-05-28 15:35', 'Обед2', 1000),
       (100001, '2007-11-08 14:15', 'Обед', 1200),
       (100001, '2007-11-08 23:00', 'Ужин', 900),
       (100001, '2007-11-13 18:15', 'Ужин', 1000),
       (100001, '2007-11-14 10:05', 'Обед', 930),
       (100001, '2007-11-14 17:05', 'Обед2', 120);
