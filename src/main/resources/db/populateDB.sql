DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories) VALUES
  (100000, '2018-07-15 9:36', 'Завтрак Пользователя', 500),
  (100000, '2018-07-16 12:36', 'Обед Пользователя', 1000),
  (100000, '2018-07-17 17:36', 'Ужин Пользователя', 300),
  (100001, '2018-07-17 9:36', 'Завтрак Админа', 150),
  (100001, '2018-07-17 12:36', 'Обед  Админа', 200),
  (100001, '2018-07-17 17:36', 'Ужин  Админа', 130);