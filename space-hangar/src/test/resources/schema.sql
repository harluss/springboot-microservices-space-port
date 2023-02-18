CREATE TABLE IF NOT EXISTS spaceships
(
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  name text NOT NULL,
  class text NOT NULL,
  payload integer NOT NULL,
  crew integer NOT NULL
);

INSERT INTO spaceships (name, class, payload, crew)
VALUES ('Razor Crest', 'Gunship', 70, 1),
       ('Slave I', 'Starfighter', 40, 1),
       ('Millenium Falcon', 'Light freighter', 100, 3),
       ('X-wing', 'Starfighter', 10, 2);
