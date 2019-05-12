--
-- DATABASE SCHEMA FOR H2
--

CREATE TABLE room (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE booking (
  id int NOT NULL AUTO_INCREMENT,
  status varchar(30) check (status in ('AVAILABLE', 'BLOCKED', 'CONFIRMED')), 
  start_time TIMESTAMP NOT NULL,
  end_time TIMESTAMP NOT NULL,
  last_update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  room_id int NOT NULL,
  CONSTRAINT fk_room FOREIGN KEY (room_id) REFERENCES room (id) ON DELETE CASCADE ON UPDATE NO ACTION,
  PRIMARY KEY (id)
);

--
-- CREATE TEST DATA
--

-- Create rooms
INSERT INTO room (name) VALUES 
('Room A'),
('Room B'),
('Room C');

-- Create time AVAILABLE slots for rooms

-- Room A
INSERT INTO booking (status, start_time, end_time, room_id) VALUES 
('AVAILABLE', '2019-05-13 09:00:00', '2019-05-13 10:00:00', (SELECT id FROM room WHERE name = 'Room A')),
('AVAILABLE', '2019-05-13 10:00:00', '2019-05-13 11:00:00', (SELECT id FROM room WHERE name = 'Room A')),
('AVAILABLE', '2019-05-13 11:00:00', '2019-05-13 12:00:00', (SELECT id FROM room WHERE name = 'Room A')),
('AVAILABLE', '2019-05-13 12:00:00', '2019-05-13 13:00:00', (SELECT id FROM room WHERE name = 'Room A')),
('AVAILABLE', '2019-05-14 16:00:00', '2019-05-14 17:00:00', (SELECT id FROM room WHERE name = 'Room A')),
('AVAILABLE', '2019-05-16 17:00:00', '2019-05-16 18:00:00', (SELECT id FROM room WHERE name = 'Room A'));

-- Room B
INSERT INTO booking (status, start_time, end_time, room_id) VALUES 
('AVAILABLE', '2019-05-15 09:00:00', '2019-05-15 10:00:00', (SELECT id FROM room WHERE name = 'Room B')),
('AVAILABLE', '2019-05-15 10:00:00', '2019-05-15 11:00:00', (SELECT id FROM room WHERE name = 'Room B')),
('AVAILABLE', '2019-05-15 11:00:00', '2019-05-15 12:00:00', (SELECT id FROM room WHERE name = 'Room B')),
('AVAILABLE', '2019-05-15 12:00:00', '2019-05-15 13:00:00', (SELECT id FROM room WHERE name = 'Room B')),
('AVAILABLE', '2019-05-15 16:00:00', '2019-05-15 17:00:00', (SELECT id FROM room WHERE name = 'Room B')),
('AVAILABLE', '2019-05-15 17:00:00', '2019-05-15 18:00:00', (SELECT id FROM room WHERE name = 'Room B'));

-- Room C
INSERT INTO booking (status, start_time, end_time, room_id) VALUES 
('AVAILABLE', '2019-05-13 09:00:00', '2019-05-13 10:00:00', (SELECT id FROM room WHERE name = 'Room C')),
('AVAILABLE', '2019-05-13 10:00:00', '2019-05-13 11:00:00', (SELECT id FROM room WHERE name = 'Room C')),
('AVAILABLE', '2019-05-16 11:00:00', '2019-05-16 12:00:00', (SELECT id FROM room WHERE name = 'Room C')),
('AVAILABLE', '2019-05-16 12:00:00', '2019-05-16 13:00:00', (SELECT id FROM room WHERE name = 'Room C')),
('AVAILABLE', '2019-05-17 16:00:00', '2019-05-17 17:00:00', (SELECT id FROM room WHERE name = 'Room C')),
('AVAILABLE', '2019-05-17 17:00:00', '2019-05-17 18:00:00', (SELECT id FROM room WHERE name = 'Room C'));