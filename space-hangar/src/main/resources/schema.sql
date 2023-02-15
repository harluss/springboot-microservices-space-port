CREATE TABLE IF NOT EXISTS spaceships
(
  id uuid PRIMARY KEY DEFAULT gen_random_uuid
(
),
  name text NOT NULL,
  payload integer NOT NULL,
  crew integer NOT NULL
  );

INSERT INTO spaceships (name, payload, crew)
VALUES ('Razor Crest', 70, 1),
       ('Slave I', 40, 1),
       ('Millenium Falcon', 100, 4);
