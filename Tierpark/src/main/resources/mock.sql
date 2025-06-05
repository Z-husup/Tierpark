-- Admins
INSERT INTO admin (username, password) VALUES
    ('admin1', 'pass1'),
    ('admin2', 'pass2'),
    ('admin3', 'pass3');

-- Enclosures
INSERT INTO enclosure (name, zone, status, type, capacity, description, condition, area) VALUES
    ('Savannah Area', 'A', 'Active', 'Outdoor', 10, 'Large open field', 'Good', '1000m²'),
    ('Reptile House', 'B', 'Under Maintenance', 'Indoor', 5, 'Warm enclosure', 'Fair', '150m²'),
    ('Bird Pavilion', 'C', 'Active', 'Indoor', 20, 'Flight-friendly dome', 'Excellent', '300m²'),
    ('Forest Zone', 'D', 'Active', 'Outdoor', 8, 'Shaded enclosure', 'Good', '800m²'),
    ('Grassland Habitat', 'E', 'Active', 'Outdoor', 6, 'For kangaroos', 'Excellent', '600m²'),
    ('Mountain Habitat', 'F', 'Active', 'Outdoor', 4, 'Rocky terrain for goats', 'Good', '700m²'),
    ('Aquatic Center', 'G', 'Active', 'Indoor', 12, 'Water-rich environment', 'Excellent', '500m²'),
    ('Nocturnal House', 'H', 'Active', 'Indoor', 7, 'Dark and quiet', 'Fair', '250m²'),
    ('Arctic Zone', 'I', 'Active', 'Outdoor', 3, 'Cold climate area', 'Excellent', '650m²'),
('Rainforest Dome', 'J', 'Active', 'Indoor', 9, 'Tropical environment', 'Good', '550m²');

-- Animals
INSERT INTO animal (name, animalGroup, dateOfBirth, arrivalDate, age, gender, size, weight, healthStatus, enclosure) VALUES
    ('Lion - Leo', 'MAMMALS', '2015-04-12', '2016-03-01', 9, 'MALE', 120, 190, 'HEALTHY', 1),
    ('Lioness - Nala', 'MAMMALS', '2016-06-05', '2017-04-11', 8, 'FEMALE', 110, 170, 'ILL', 1),
    ('Elephant - Dumbo', 'MAMMALS', '2010-09-01', '2011-05-15', 14, 'MALE', 300, 5000, 'ILL', 4),
    ('Penguin - Happy Feet', 'BIRDS', '2020-12-25', '2021-01-10', 4, 'MALE', 40, 15, 'HEALTHY', 3),
    ('Snake - Nagini', 'REPTILES', '2018-07-18', '2019-03-07', 6, 'FEMALE', 30, 8, 'HEALTHY', 2),
    ('Kangaroo - Roo', 'MAMMALS', '2019-11-10', '2020-02-20', 5, 'FEMALE', 80, 85, 'HEALTHY', 5),
    ('Tiger - Shera', 'MAMMALS', '2014-08-12', '2015-01-10', 10, 'MALE', 130, 220, 'HEALTHY', 1),
    ('Giraffe - Tallie', 'MAMMALS', '2012-03-03', '2013-07-22', 12, 'FEMALE', 500, 900, 'HEALTHY', 4),
    ('Owl - Hooty', 'BIRDS', '2021-02-18', '2021-03-10', 3, 'FEMALE', 25, 1.5, 'HEALTHY', 3),
    ('Crocodile - Snap', 'REPTILES', '2013-05-05', '2014-06-01', 11, 'MALE', 200, 600, 'HEALTHY', 2),
    ('Monkey - Kiki', 'MAMMALS', '2017-04-01', '2018-02-15', 7, 'FEMALE', 70, 35, 'HEALTHY', 5),
    ('Parrot - Polly', 'BIRDS', '2019-08-12', '2019-09-01', 5, 'MALE', 20, 0.9, 'HEALTHY', 3);

-- AnimalFood
INSERT INTO animalfood (name, weight, deliveryDate, expirationDate, storageCondition, quantity) VALUES
    ('Meat Chunks', 100, '2025-05-01', '2025-06-01', 'Frozen', 20),
    ('Bananas', 50, '2025-05-10', '2025-05-25', 'Cool', 30),
    ('Fish', 75, '2025-05-05', '2025-05-30', 'Frozen', 25),
    ('Rodents', 40, '2025-05-15', '2025-06-10', 'Frozen', 15),
    ('Grass Pellets', 60, '2025-05-08', '2025-06-08', 'Dry', 40),
    ('Chicken Legs', 90, '2025-05-11', '2025-06-01', 'Frozen', 18),
    ('Apples', 45, '2025-05-12', '2025-05-26', 'Cool', 22),
    ('Insects Mix', 30, '2025-05-09', '2025-05-30', 'Cool', 12),
    ('Hay Bales', 200, '2025-05-06', '2025-07-01', 'Dry', 50),
    ('Nuts and Berries', 35, '2025-05-14', '2025-06-05', 'Cool', 16);

-- MedicalHistory
INSERT INTO medicalhistory (animalId, date, description) VALUES
    (1, '2025-04-01', 'Annual health checkup - normal'),
    (2, '2025-03-15', 'Vaccinated for rabies'),
    (3, '2025-02-20', 'Minor leg injury treated'),
    (4, '2025-01-10', 'Nutritional evaluation done'),
    (5, '2025-04-20', 'Shed skin inspected - no issues'),
    (6, '2025-03-01', 'Routine dental check'),
    (7, '2025-02-25', 'X-ray taken for chest pain'),
    (8, '2025-03-30', 'Neck sprain diagnosed'),
    (9, '2025-05-01', 'Wing feathers trimmed'),
    (10, '2025-04-11', 'Eye infection treated');

-- Schedule
-- Schedule (×3)
INSERT INTO schedule (description, scheduleType, startingTime, enclosure) VALUES
    ('Morning Feeding', 'FEEDING', '2025-06-01', 1),
    ('Cleaning Shift', 'ENCLOSURE_CLEANING', '2025-06-01', 2),
    ('Vet Check', 'TREATMENT', '2025-06-02', 3),
    ('Evening Feeding', 'FEEDING', '2025-06-01', 4),
    ('Medical Treatment', 'TREATMENT', '2025-06-03', 5),
    ('Water Tank Check', 'FEEDING', '2025-06-04', 6),
    ('Feeding Time', 'FEEDING', '2025-06-02', 7),
    ('Habitat Inspection', 'ENCLOSURE_CLEANING', '2025-06-05', 8),
    ('Medical Checkup', 'TREATMENT', '2025-06-04', 9),
    ('Deep Cleaning', 'ENCLOSURE_CLEANING', '2025-06-06', 10),

    ('Morning Feeding 2', 'FEEDING', '2025-06-07', 1),
    ('Cleaning Shift 2', 'ENCLOSURE_CLEANING', '2025-06-07', 2),
    ('Vet Check 2', 'TREATMENT', '2025-06-08', 3),
    ('Evening Feeding 2', 'FEEDING', '2025-06-07', 4),
    ('Medical Treatment 2', 'TREATMENT', '2025-06-09', 5),
    ('Water Refill', 'FEEDING', '2025-06-10', 6),
    ('Supplement Feeding', 'FEEDING', '2025-06-08', 7),
    ('Enclosure Sanitization', 'ENCLOSURE_CLEANING', '2025-06-09', 8),
    ('Routine Checkup', 'TREATMENT', '2025-06-10', 9),
    ('General Maintenance', 'ENCLOSURE_CLEANING', '2025-06-11', 10),

    ('Morning Feeding 3', 'FEEDING', '2025-06-12', 1),
    ('Cleaning Shift 3', 'ENCLOSURE_CLEANING', '2025-06-12', 2),
    ('Vet Check 3', 'TREATMENT', '2025-06-13', 3),
    ('Evening Feeding 3', 'FEEDING', '2025-06-12', 4),
    ('Medical Treatment 3', 'TREATMENT', '2025-06-14', 5),
    ('Filter Check', 'FEEDING', '2025-06-15', 6),
    ('Protein Feeding', 'FEEDING', '2025-06-13', 7),
    ('Glass Cleaning', 'ENCLOSURE_CLEANING', '2025-06-14', 8),
    ('Vaccination Prep', 'TREATMENT', '2025-06-15', 9),
    ('Barrier Inspection', 'ENCLOSURE_CLEANING', '2025-06-16', 10);


-- Worker
INSERT INTO worker (username, password, fullName, email, phoneNumber, dateOfBirth, gender, hireDate, status, salary, specialization, enclosure) VALUES
    ('worker1', 'pass1', 'Zhusup Akylbek Aziz', 'zaz@zoo.com', '01771234567', '1984-03-21', 'MALE', '2016-05-01', 'WORKING', 4200, 'MEDICAL_ASSISTANCE', 2),
    ('worker2', 'pass2', 'Lara Schmidt', 'lara.schmidt@zoo.com', '01779887766', '1990-07-14', 'FEMALE', '2021-02-10', 'WORKING', 2600, 'SUPERVISION', 1),
    ('worker3', 'pass3', 'Boris Ivanov', 'boris.ivanov@zoo.com', '01774563211', '1988-11-02', 'MALE', '2019-09-01', 'VACATION', 2500, 'SUPERVISION', 3),
    ('worker4', 'pass4', 'Julia Becker', 'julia.becker@zoo.com', '01776543210', '1982-01-17', 'FEMALE', '2015-01-01', 'WORKING', 3800, 'MEDICAL_ASSISTANCE', 5),
    ('worker5', 'pass5', 'Marcus Weber', 'marcus.weber@zoo.com', '01773456789', '1995-05-25', 'MALE', '2023-04-05', 'WORKING', 2200, 'MEDICAL_ASSISTANCE', 2),
    ('worker6', 'pass6', 'Ingrid Müller', 'ingrid.mueller@zoo.com', '01775678901', '1960-08-10', 'FEMALE', '2000-03-12', 'RETIRED', 0, 'SUPERVISION', 4);
