DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (LOGIN, EMAIL, NAME, PASSWORD) VALUES
  ('User', 'user@yandex.ru', 'User Name', 'password'),
  ('Admin', 'admin@gmail.com', 'Admin Name', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001),
  ('ROLE_USER', 100001);