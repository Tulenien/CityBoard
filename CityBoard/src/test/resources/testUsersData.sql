-- user1 has ROLE_ADMIN, ROLE_MOD and ROLE_USER
-- user 2 has ROLE_MOD and ROLE_USER
-- user 3 has ROLE_MOD
-- users 4 to 6 has ROLE_USER
-- user7 has no role, password_expired
-- 5 users, 3 mods, 1 admin

insert into users (username, password_expired) values
                  ('user1', false),
                  ('user2', false),
                  ('user3', false),
                  ('user4', false),
                  ('user5', false),
                  ('user6', false),
                  ('user7', true);

insert into users_roles (user_id, roles) values
                  (1, 'ROLE_ADMIN'),
                  (1, 'ROLE_MOD'),
                  (1, 'ROLE_USER'),
                  (2, 'ROLE_MOD'),
                  (2, 'ROLE_USER'),
                  (3, 'ROLE_MOD'),
                  (4, 'ROLE_USER'),
                  (5, 'ROLE_USER'),
                  (6, 'ROLE_USER');