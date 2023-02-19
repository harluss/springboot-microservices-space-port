CREATE TABLE IF NOT EXISTS pilots
(
  id         uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  name       text NOT NULL,
  species    text NOT NULL,
  profession text NOT NULL,
  weapons    text[]
);

INSERT INTO pilots (name, species, profession, weapons)
VALUES ('Han Solo', 'Human', 'Smuggler', ARRAY ['DL-44 heavy blaster pistol']),
       ('Chewbacca', 'Wookiee', 'Smuggler', ARRAY ['Bowcaster']),
       ('Boba Fett', 'Human', 'Bounty hunter', ARRAY ['EE-3 blaster rifle', 'Z-X Flame-projector', 'Z-6 Jetpack']),
       ('Mando', 'Human', 'Bounty hunter', ARRAY ['IB-94 blaster pistol', 'Beskar spear', 'Darksaber']),
       ('R2D2', 'Droid', 'Mechanic', ARRAY []::text[]),
       ('Luke Skywalker', 'Human', 'Jedi Master', ARRAY ['Lightsaber']),
       ('Greedo', 'Rodian', 'Bounty hunter', ARRAY ['Blaster pistol']),
       ('IG-11', 'Driod', 'Assassin',
        ARRAY ['E-11 medium blaster rifle', 'DLT-20A blaster rifle', 'Self-destruct mechanism']);
