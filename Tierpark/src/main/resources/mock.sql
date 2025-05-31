-- Admins
INSERT INTO admin (username, password) VALUES
                                           ('admin1', 'pass123'),
                                           ('admin2', 'adminpass'),
                                           ('zooMaster', 'securezoo');

-- Species
INSERT INTO species (name, habitat, animalClass, diet) VALUES
                                                           ('Lion', 'Savannah', 'Mammal', 'Carnivore'),
                                                           ('Elephant', 'Forest', 'Mammal', 'Herbivore'),
                                                           ('Penguin', 'Arctic', 'Bird', 'Carnivore'),
                                                           ('Cobra', 'Desert', 'Reptile', 'Carnivore'),
                                                           ('Kangaroo', 'Grassland', 'Mammal', 'Herbivore');

-- AnimalStatus
INSERT INTO animalstatus (healthStatus, breedingStatus, isDead) VALUES
                                                                    ('Healthy', 'Breeding', 'No'),
                                                                    ('Injured', 'Not Breeding', 'No'),
                                                                    ('Sick', 'Not Breeding', 'No'),
                                                                    ('Healthy', 'Breeding', 'Yes'),
                                                                    ('Healthy', 'Not Breeding', 'No');

-- Enclosures
INSERT INTO enclosure (name, zone, status, type, capacity, description, condition, area) VALUES
                                                                                             ('Savannah Area', 'A', 'Active', 'Outdoor', 10, 'Large open field', 'Good', '1000m²'),
                                                                                             ('Reptile House', 'B', 'Under Maintenance', 'Indoor', 5, 'Warm enclosure', 'Fair', '150m²'),
                                                                                             ('Bird Pavilion', 'C', 'Active', 'Indoor', 20, 'Flight-friendly dome', 'Excellent', '300m²'),
                                                                                             ('Forest Zone', 'D', 'Active', 'Outdoor', 8, 'Shaded enclosure', 'Good', '800m²'),
                                                                                             ('Grassland Habitat', 'E', 'Active', 'Outdoor', 6, 'For kangaroos', 'Excellent', '600m²');

-- Animals
INSERT INTO animal (name, species, dateOfBirth, arrivalDate, age, gender, size, weight, status, enclosure) VALUES
                                                                                                               ('Leo', 1, '2015-04-12', '2016-03-01', 9, 'MALE', 120, 190, 1, 1),
                                                                                                               ('Nala', 1, '2016-06-05', '2017-04-11', 8, 'FEMALE', 110, 170, 1, 1),
                                                                                                               ('Dumbo', 2, '2010-09-01', '2011-05-15', 14, 'MALE', 300, 5000, 1, 4),
                                                                                                               ('Happy Feet', 3, '2020-12-25', '2021-01-10', 4, 'MALE', 40, 15, 1, 3),
                                                                                                               ('Nagini', 4, '2018-07-18', '2019-03-07', 6, 'FEMALE', 30, 8, 2, 2),
                                                                                                               ('Roo', 5, '2019-11-10', '2020-02-20', 5, 'FEMALE', 80, 85, 1, 5);

-- AnimalFood
INSERT INTO animalfood (name, weight, deliveryDate, expirationDate, storageCondition, quantity) VALUES
                                                                                                    ('Meat Chunks', 100, '2025-05-01', '2025-06-01', 'Frozen', 20),
                                                                                                    ('Bananas', 50, '2025-05-10', '2025-05-25', 'Cool', 30),
                                                                                                    ('Fish', 75, '2025-05-05', '2025-05-30', 'Frozen', 25),
                                                                                                    ('Rodents', 40, '2025-05-15', '2025-06-10', 'Frozen', 15),
                                                                                                    ('Grass Pellets', 60, '2025-05-08', '2025-06-08', 'Dry', 40);

-- MedicalHistory
INSERT INTO medicalhistory (animalId, description) VALUES
                                                       (1, 'Annual health checkup - normal'),
                                                       (2, 'Vaccinated for rabies'),
                                                       (3, 'Minor leg injury treated'),
                                                       (4, 'Nutritional evaluation done'),
                                                       (5, 'Shed skin inspected - no issues');

-- Schedule
INSERT INTO schedule (name, scheduleType, startingTime, enclosure) VALUES
                                                                       ('Morning Feeding', 'FEEDING', '2025-06-01', 1),
                                                                       ('Cleaning Shift', 'ENCLOSURE_CLEANING', '2025-06-01', 2),
                                                                       ('Vet Check', 'TREATMENT', '2025-06-02', 3),
                                                                       ('Evening Feeding', 'FEEDING', '2025-06-01', 4),
                                                                       ('Medical Treatment', 'TREATMENT', '2025-06-03', 5);

-- Worker
INSERT INTO worker (username, password, fullName, email, phoneNumber, dateOfBirth, gender, hireDate, status, salary, specialization, enclosure) VALUES
                                                                                                                                                    ('worker1', 'pass1', 'Alice Smith', 'alice@zoo.com', '1234567890', '1990-04-20', 'FEMALE', '2020-05-01', 'WORKING', 2500, 'MEDICAL_ASSISTANCE', 1),
                                                                                                                                                    ('worker2', 'pass2', 'Bob Johnson', 'bob@zoo.com', '0987654321', '1985-08-15', 'MALE', '2019-03-10', 'VACATION', 2300, 'FEEDING', 2),
                                                                                                                                                    ('worker3', 'pass3', 'Clara Adams', 'clara@zoo.com', '555666777', '1995-12-05', 'FEMALE', '2021-07-20', 'WORKING', 2400, 'SUPERVISION', 3),
                                                                                                                                                    ('worker4', 'pass4', 'David Lee', 'david@zoo.com', '444333222', '1988-02-28', 'MALE', '2018-11-11', 'RETIRED', 2600, 'ADMINISTRATION', 4),
                                                                                                                                                    ('worker5', 'pass5', 'Eva Zhang', 'eva@zoo.com', '321654987', '1992-09-09', 'FEMALE', '2022-01-15', 'WORKING', 2450, 'FEEDING', 5);
