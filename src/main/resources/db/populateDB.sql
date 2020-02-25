DELETE
FROM meals;
DELETE
FROM user_roles;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001);

INSERT INTO meals (id, user_id, datetime, description, calories)
VALUES (1, 100000, ('2020-01-30 10:00'), 'Завтрак', 500),
       (2, 100000, ('2020-01-30 13:00'), 'Обед', 1000),
       (3, 100000, ('2020-01-30 20:00'), 'Ужин', 500),
       (4, 100000, ('2020-01-30 00:00'), 'Еда на граничное значение', 100),
       (5, 100001, ('2020-01-31 10:00'), 'Завтрак', 1000),
       (6, 100001, ('2020-01-31 13:00'), 'Обед', 500),
       (7, 100001, ('2020-01-31 20:00'), 'Ужин', 410);

