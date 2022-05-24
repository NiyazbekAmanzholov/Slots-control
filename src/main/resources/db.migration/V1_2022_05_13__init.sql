CREATE TABLE city
(
    id                  INT PRIMARY KEY AUTO_INCREMENT,
    name                VARCHAR(255)
);

CREATE TABLE slots
(
    id                  INT PRIMARY KEY AUTO_INCREMENT,
    time_start          TIMESTAMP,
    time_stop           TIMESTAMP,
    kbt                 INT DEFAULT 0,
    not_kbt             INT DEFAULT 0,
    city_id             INT NOT NULL DEFAULT 0,
    CONSTRAINT FOREIGN KEY (city_id) REFERENCES city (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE reserved_slots
(
    id                  INT PRIMARY KEY AUTO_INCREMENT,
    delivery_date       TIMESTAMP,
    reserved_kbt        INT DEFAULT 0,
    reserved_not_kbt    INT DEFAULT 0,
    slot_id             INT NOT NULL DEFAULT 0,
    CONSTRAINT FOREIGN KEY (slot_id) REFERENCES slots (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

INSERT INTO city (id, name) VALUES (1, 'Алмата');
INSERT INTO city (id, name) VALUES (2, 'Нур-Султан');
INSERT INTO city (id, name) VALUES (3, 'Караганда');
INSERT INTO city (id, name) VALUES (4, 'Павлодар');
INSERT INTO city (id, name) VALUES (5, 'Костанай');
INSERT INTO city (id, name) VALUES (6, 'Чимкент');
INSERT INTO city (id, name) VALUES (7, 'Актау');
INSERT INTO city (id, name) VALUES (8, 'Атырау');

INSERT INTO slots (id, time_start, time_stop, kbt, not_kbt, city_id) VALUES (1, '2022-05-16 10:00:00.000000', '2022-05-16 13:00:00.000000', 10, 20, 1);
INSERT INTO slots (id, time_start, time_stop, kbt, not_kbt, city_id) VALUES (2, '2022-05-16 14:00:00.000000', '2022-05-16 18:00:00.000000', 10, 20, 1);
INSERT INTO slots (id, time_start, time_stop, kbt, not_kbt, city_id) VALUES (3, '2022-05-17 15:19:38.000000', '2022-05-17 20:20:01.000000', 10, 20, 2);
INSERT INTO slots (id, time_start, time_stop, kbt, not_kbt, city_id) VALUES (4, '2022-05-18 08:20:41.000000', '2022-05-18 15:21:03.000000', 10, 20, 3);
INSERT INTO slots (id, time_start, time_stop, kbt, not_kbt, city_id) VALUES (5, '2022-05-19 15:45:50.000000', '2022-05-19 20:46:31.000000', 10, 20, 4);
INSERT INTO slots (id, time_start, time_stop, kbt, not_kbt, city_id) VALUES (6, '2022-05-20 15:47:05.000000', '2022-05-20 20:47:15.000000', 10, 20, 5);
INSERT INTO slots (id, time_start, time_stop, kbt, not_kbt, city_id) VALUES (7, '2022-05-21 15:48:49.000000', '2022-05-21 20:49:08.000000', 10, 20, 6);
INSERT INTO slots (id, time_start, time_stop, kbt, not_kbt, city_id) VALUES (8, '2022-05-22 11:50:05.000000', '2022-05-22 15:50:15.000000', 10, 20, 7);
INSERT INTO slots (id, time_start, time_stop, kbt, not_kbt, city_id) VALUES (9, '2022-05-23 09:50:51.000000', '2022-05-23 18:51:01.000000', 10, 20, 8);

INSERT INTO reserved_slots (id, delivery_date, reserved_kbt, reserved_not_kbt, slot_id) VALUES (1, '2022-05-16 10:00:00.000000', 1, 2, 1);
INSERT INTO reserved_slots (id, delivery_date, reserved_kbt, reserved_not_kbt, slot_id) VALUES (2, '2022-05-16 14:00:00.000000', 1, 2, 2);
INSERT INTO reserved_slots (id, delivery_date, reserved_kbt, reserved_not_kbt, slot_id) VALUES (3, '2022-05-17 15:54:12.000000', 2, 3, 3);
INSERT INTO reserved_slots (id, delivery_date, reserved_kbt, reserved_not_kbt, slot_id) VALUES (4, '2022-05-18 15:54:21.000000', 3, 4, 4);
INSERT INTO reserved_slots (id, delivery_date, reserved_kbt, reserved_not_kbt, slot_id) VALUES (5, '2022-05-19 15:54:27.000000', 4, 5, 5);
INSERT INTO reserved_slots (id, delivery_date, reserved_kbt, reserved_not_kbt, slot_id) VALUES (8, '2022-05-22 15:54:41.000000', 7, 10, 8);
INSERT INTO reserved_slots (id, delivery_date, reserved_kbt, reserved_not_kbt, slot_id) VALUES (9, '2022-05-23 15:54:46.000000', 6, 7, 9);
INSERT INTO reserved_slots (id, delivery_date, reserved_kbt, reserved_not_kbt, slot_id) VALUES (6, '2022-05-20 15:54:31.000000', 5, 6, 6);
INSERT INTO reserved_slots (id, delivery_date, reserved_kbt, reserved_not_kbt, slot_id) VALUES (7, '2022-05-21 15:54:36.000000', 9, 10, 7);