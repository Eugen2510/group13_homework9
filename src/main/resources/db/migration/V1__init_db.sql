CREATE TABLE IF NOT EXISTS building (
id INT AUTO_INCREMENT PRIMARY KEY,
num_of_flats SMALLiNT NOT NULL DEFAULT 100,
address VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS flat(
id INT AUTO_INCREMENT PRIMARY KEY,
building_id INT,
apartment_number SMALLINT NOT NULL,
area SMALLINT NOT NULL DEFAULT 60,
floor TINYINT NOT NULL,
num_of_rooms TINYINT DEFAULT 1,
FOREIGN KEY flat_building_fk (building_id) REFERENCES building(id)
);


CREATE TABLE IF NOT EXISTS person (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100) NOT NULL,
email VARCHAR(100) NOT NULL,
phone VARCHAR(14) NOT NULL,
parking_right SMALLINT DEFAULT 0, CHECK (parking_right IN (0,1)),
residential_flat INT,
FOREIGN KEY (residential_flat) REFERENCES flat(id)

);

CREATE TABLE IF NOT EXISTS ownership (
flat_id INT,
person_id INT,
PRIMARY KEY(flat_id, person_id),
FOREIGN KEY (person_id) REFERENCES person(id) ,
FOREIGN KEY (flat_id) REFERENCES flat(id)
);
