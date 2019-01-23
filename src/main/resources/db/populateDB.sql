DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM RESTAURANTS;
ALTER SEQUENCE global_seq
RESTART WITH 100000;

INSERT INTO users (LOGIN, EMAIL, NAME, PASSWORD)
VALUES ('User', 'user@yandex.ru', 'User Name', 'password'),
       ('Admin', 'admin@gmail.com', 'Admin Name', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001),
       ('ROLE_USER', 100001);

INSERT INTO RESTAURANTS (NAME, ADDRESS)
VALUES ('RESTAURANT1', 'ADDRESS1'), -- id = 100002
       ('RESTAURANT2', 'ADDRESS2'); -- id = 100003

INSERT INTO MENU_ITEMS (RESTAURANT_ID, DAY, NAME, PRICE)
VALUES (100002, NOW, 'Картошка', 39),
       (100002, NOW, 'Котлета', 99);
