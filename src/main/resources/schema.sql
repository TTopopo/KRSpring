CREATE TABLE customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    patronymic VARCHAR(50) NOT NULL,
    phone_number VARCHAR(15) NOT NULL
);

CREATE TABLE foreman (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    patronymic VARCHAR(50) NOT NULL,
    specialization VARCHAR(50) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    qualification VARCHAR(50) NOT NULL,
    customer_id BIGINT,
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE worker (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    patronymic VARCHAR(50),
    phone_number VARCHAR(15) NOT NULL,
    position VARCHAR(50) NOT NULL,
    foreman_id BIGINT,
    FOREIGN KEY (foreman_id) REFERENCES foreman(id)
);

CREATE TABLE object (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    address VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    customer_id BIGINT,
    foreman_id BIGINT,
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (foreman_id) REFERENCES foreman(id)
);

CREATE TABLE worker_object (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    worker_id BIGINT,
    object_id BIGINT,
    FOREIGN KEY (worker_id) REFERENCES worker(id),
    FOREIGN KEY (object_id) REFERENCES object(id)
);
