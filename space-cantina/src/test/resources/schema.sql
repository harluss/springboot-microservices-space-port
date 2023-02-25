CREATE TABLE IF NOT EXISTS pilots
(
  id         uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  name       text UNIQUE NOT NULL,
  species    text        NOT NULL,
  profession text        NOT NULL,
  weapons    text[]
);

INSERT INTO pilots (id, name, species, profession, weapons)
VALUES ('8ed6e335-56cb-4512-b1fb-5a55faa1057c', 'Han Solo', 'Human', 'Smuggler', ARRAY ['DL-44 heavy blaster pistol']),
       ('e1d4e41b-c72e-4fb7-b3bd-6b86e96b20f1', 'Chewbacca', 'Wookiee', 'Smuggler', ARRAY ['Bowcaster']),
       ('c77821c7-6b82-4d43-9837-2f7768610f45', 'Boba Fett', 'Human', 'Bounty hunter',
        ARRAY ['EE-3 blaster rifle', 'Z-X Flame-projector', 'Z-6 Jetpack']),
       ('84ced9d9-fb61-4eb0-a1c8-1c1ddf369ef1', 'Mando', 'Human', 'Bounty hunter',
        ARRAY ['IB-94 blaster pistol', 'Beskar spear', 'Darksaber']),
       ('1b6fc0c9-ab4c-4c01-98e3-7dac5395bdef', 'R2D2', 'Droid', 'Mechanic', ARRAY []::text[]),
       ('e9ae8934-38ba-432b-a1c0-caa03ea73af9', 'Luke Skywalker', 'Human', 'Jedi Master', ARRAY ['Lightsaber']),
       ('58ce4ada-ecac-454d-abfe-1a7ed8ae9ccf', 'Greedo', 'Rodian', 'Bounty hunter', ARRAY ['Blaster pistol']),
       ('b91e6a4a-cfca-4fd3-8495-9701ea916abc', 'IG-11', 'Driod', 'Assassin',
        ARRAY ['E-11 medium blaster rifle', 'DLT-20A blaster rifle', 'Self-destruct mechanism'])
ON CONFLICT (name) DO NOTHING;

