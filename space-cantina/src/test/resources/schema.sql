CREATE TABLE IF NOT EXISTS pilots
(
  id         uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  name       text NOT NULL,
  species    text NOT NULL,
  profession text NOT NULL,
  weapons    text[]
);

INSERT INTO pilots (name, species, profession, weapons)
VALUES ('Han Solo', 'Human', 'Smuggler', ARRAY ['Blaster pistol']),
       ('Chewbacca', 'Wookiee', 'Smuggler', ARRAY ['Bowcaster']),
       ('Boba Fett', 'Human', 'Bounty hunter', ARRAY ['Blaster', 'Flame thrower']),
       ('Mando', 'Human', 'Bounty hunter', ARRAY ['Blaster pistol']),
       ('R2D2', 'Droid', 'Mechanic', ARRAY []::text[]),
       ('Luke Skywalker', 'Human', 'Jedi Master', ARRAY ['Lightsaber']),
       ('Greedo', 'Rodian', 'Bounty hunter', ARRAY ['Blaster pistol']),
       ('IG-11', 'Driod', 'Assassin', ARRAY ['Blaster rifle']);
