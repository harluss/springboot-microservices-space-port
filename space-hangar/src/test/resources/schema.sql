CREATE TABLE IF NOT EXISTS spaceships
(
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  name text NOT NULL,
  class text NOT NULL,
  max_speed integer NOT NULL,
  payload integer NOT NULL,
  crew_ids uuid[],
  armament text[]
);

INSERT INTO spaceships (id, name, class, max_speed, payload, crew_ids, armament)
VALUES ('d71649e9-c383-4e13-8d81-1f4e0145f3f0', 'Razor Crest', 'Gunship', 800, 70, ARRAY ['84ced9d9-fb61-4eb0-a1c8-1c1ddf369ef1'::uuid, 'b91e6a4a-cfca-4fd3-8495-9701ea916abc'::uuid], ARRAY ['Heavy laser cannons']),
       ('950126bc-1e4b-4792-a018-0d588660d499', 'Slave I', 'Starfighter', 1000, 40, ARRAY ['c77821c7-6b82-4d43-9837-2f7768610f45'::uuid], ARRAY ['Twin rotating blaster cannonTwin rotating blaster cannon', 'Proton torpedo tubes', 'Seismic charges']),
       ('9e075e25-3bd8-466e-b978-cf38a07ff85b', 'Millennium Falcon', 'Light freighter', 1200, 100, ARRAY ['8ed6e335-56cb-4512-b1fb-5a55faa1057c'::uuid, 'e1d4e41b-c72e-4fb7-b3bd-6b86e96b20f1'::uuid], ARRAY ['Quad laser cannons', 'Concussion missile tubes']),
       ('3fa85f64-5717-4562-b3fc-2c963f66afa6', 'X-wing', 'Starfighter', 1050, 10, ARRAY ['e9ae8934-38ba-432b-a1c0-caa03ea73af9'::uuid, '1b6fc0c9-ab4c-4c01-98e3-7dac5395bdef'::uuid], ARRAY ['Laser cannons', 'Proton torpedo launchers']);
