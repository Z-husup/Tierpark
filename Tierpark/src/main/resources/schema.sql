-- Table for Admin
CREATE TABLE admin (
                       id INT PRIMARY KEY,
                       username VARCHAR(255),
                       password VARCHAR(255)
);

-- Table for Species
CREATE TABLE species (
                         id BIGINT PRIMARY KEY,
                         name VARCHAR(255),
                         habitat VARCHAR(255),
                         animalClass VARCHAR(255),
                         diet VARCHAR(255)
);

-- Table for AnimalStatus
CREATE TABLE animalstatus (
                              id BIGINT PRIMARY KEY,
                              healthStatus VARCHAR(255),
                              breedingStatus VARCHAR(255),
                              isDead VARCHAR(255)
);

-- Table for Enclosure
CREATE TABLE enclosure (
                           id BIGINT PRIMARY KEY,
                           name VARCHAR(255),
                           zone VARCHAR(255),
                           status VARCHAR(255),
                           type VARCHAR(255),
                           capacity INT,
                           description VARCHAR(255),
                           condition VARCHAR(255),
                           area VARCHAR(255)
);

-- Table for Animal
CREATE TABLE animal (
                        id BIGINT PRIMARY KEY,
                        name VARCHAR(255),
                        species BIGINT,
                        dateOfBirth DATE,
                        arrivalDate DATE,
                        age INT,
                        gender VARCHAR(255),
                        size INT,
                        weight INT,
                        status BIGINT,
                        enclosure BIGINT,
                        FOREIGN KEY (species) REFERENCES species(id),
                        FOREIGN KEY (status) REFERENCES animalstatus(id),
                        FOREIGN KEY (enclosure) REFERENCES enclosure(id)
);

-- Table for AnimalFood
CREATE TABLE animalfood (
                            id BIGINT PRIMARY KEY,
                            name VARCHAR(255),
                            weight INT,
                            deliveryDate DATE,
                            expirationDate DATE,
                            storageCondition VARCHAR(255),
                            quantity INTEGER
);

-- Table for MedicalHistory
CREATE TABLE medicalhistory (
                                id INT PRIMARY KEY,
                                animalId BIGINT,
                                description VARCHAR(255),
                                FOREIGN KEY (animalId) REFERENCES animal(id)
);

-- Table for Schedule
CREATE TABLE schedule (
                          id BIGINT PRIMARY KEY,
                          name VARCHAR(255),
                          scheduleType VARCHAR(255),
                          startingTime DATE,
                          enclosure BIGINT,
                          FOREIGN KEY (enclosure) REFERENCES enclosure(id)
);

-- Table for Worker
CREATE TABLE worker (
                        id BIGINT PRIMARY KEY,
                        username VARCHAR(255),
                        password VARCHAR(255),
                        fullName VARCHAR(255),
                        email VARCHAR(255),
                        phoneNumber VARCHAR(255),
                        dateOfBirth DATE,
                        gender VARCHAR(255),
                        hireDate DATE,
                        status VARCHAR(255),
                        salary INT,
                        specialization VARCHAR(255),
                        enclosure BIGINT,
                        FOREIGN KEY (enclosure) REFERENCES enclosure(id)
);
