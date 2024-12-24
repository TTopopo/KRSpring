-- Добавление данных в таблицу customer
INSERT INTO customer (name, surname, patronymic, phone_number) VALUES ('Иван', 'Иванов', 'Иванович', '1234567890');
INSERT INTO customer (name, surname, patronymic, phone_number) VALUES ('Мария', 'Смирнова', 'Смирнова', '9876543210');
INSERT INTO customer (name, surname, patronymic, phone_number) VALUES ('Алексей', 'Кузнецов', 'Кузнецов', '8765432109');

-- Добавление данных в таблицу foreman
INSERT INTO foreman (name, surname, patronymic, specialization, phone_number, qualification, customer_id) VALUES ('Петр', 'Петров', 'Петрович', 'Строитель', '0987654321', 'Высокая', 1);
INSERT INTO foreman (name, surname, patronymic, specialization, phone_number, qualification, customer_id) VALUES ('Екатерина', 'Екатеринова', 'Екатериновна', 'Инженер', '3210987654', 'Высокая', 2);

-- Добавление данных в таблицу worker
INSERT INTO worker (name, surname, patronymic, phone_number, position, foreman_id) VALUES ('Анна', 'Антонова', 'Антоновна', '1112223333', 'Рабочий', 1);
INSERT INTO worker (name, surname, patronymic, phone_number, position, foreman_id) VALUES ('Дмитрий', 'Дмитриев', 'Дмитриевич', '5432109876', 'Рабочий', 2);

-- Добавление данных в таблицу object
INSERT INTO object (type, name, address, status, customer_id, foreman_id) VALUES ('Здание', 'Жилой дом', 'ул. Ленина, 10', 'В процессе', 1, 1);
INSERT INTO object (type, name, address, status, customer_id, foreman_id) VALUES ('Здание', 'Офисное здание', 'ул. Пушкина, 5', 'Завершено', 2, 2);
INSERT INTO object (type, name, address, status, customer_id, foreman_id) VALUES ('Мост', 'Мост через реку', 'ул. Речная, 2', 'В процессе', 3, 1);
