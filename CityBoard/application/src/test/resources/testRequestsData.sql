insert into users (username, password_expired) values
                  ('User1', false),
                  ('User2', false),
                  ('User3', false);

insert into adverts (type, mod_check) values
                    (0, true),
                    (1, true),
                    (0, true);

-- advert 1 (4 req) - rent
-- advert 2 (4 req) - sale
-- advert 3 (4 req) - rent, all requests deleted

-- type 0 - rent
-- type 1 - sale
-- type 2 - viewing

-- status 0 - pending
-- status 1 - rejected
-- status 2 - accepted
-- status 3 - deleted
insert into requests (type, status, advert_id, author_id) values
-- user 1 - rent, pending; sale, rejected; viewing, deleted; rent, deleted;
                     (0, 0, 1, 1),
                     (1, 1, 2, 1),
                     (2, 3, 3, 1),
                     (0, 3, 3, 1),
-- user 2 - viewing+rent, accepted; sale, pending; rent, deleted;
                     (2, 2, 1, 2),
                     (1, 0, 2, 2),
                     (0, 3, 3, 2),
-- user 3 - sale, accepted; rent, rejected; viewing+sale, accepted, viewing+rent, rejected; viewing+rent, deleted;
                     (1, 2, 2, 3),
                     (0, 1, 1, 3),
                     (2, 2, 2, 3),
                     (2, 1, 1, 3),
                     (0, 3, 3, 3);