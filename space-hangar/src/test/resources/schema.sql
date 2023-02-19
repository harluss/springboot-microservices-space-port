CREATE TABLE IF NOT EXISTS spaceships
(
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  name text NOT NULL,
  class text NOT NULL,
  max_speed integer NOT NULL,
  payload integer NOT NULL,
  crew integer NOT NULL,
  crew_list uuid[],
  armament text[]
);

INSERT INTO spaceships (name, class, max_speed, payload, crew, crew_list, armament)
VALUES ('Razor Crest', 'Gunship', 800, 70, 2, ARRAY []::uuid[], ARRAY ['Heavy laser cannons']),
       ('Slave I', 'Starfighter', 1000, 40, 1, ARRAY []::uuid[], ARRAY ['Twin rotating blaster cannonTwin rotating blaster cannon', 'Proton torpedo tubes', 'Seismic charges']),
       ('Millennium Falcon', 'Light freighter', 1200, 100, 2, ARRAY []::uuid[], ARRAY ['Quad laser cannons', 'Concussion missile tubes']),
       ('X-wing', 'Starfighter', 1050, 10, 2, ARRAY []::uuid[], ARRAY ['Laser cannons', 'Proton torpedo launchers']);
