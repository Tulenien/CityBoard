-- type 0 - rent
-- type 1 - sale

-- status 0 - visible
-- status 1 - hidden
-- status 2 - deleted

insert into adverts (mod_check, type, status) values
                    (false, 0, 0),
                    (false, 0, 1),
                    (false, 0, 2),
                    (false, 1, 0),
                    (false, 1, 1),
                    (false, 1, 2),
                    (true, 0, 0),
                    (true, 0, 1),
                    (true, 0, 2),
                    (true, 1, 0),
                    (true, 1, 1),
                    (true, 1, 2);