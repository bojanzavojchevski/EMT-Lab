CREATE TABLE country (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    continent VARCHAR(100) NOT NULL
);

CREATE TABLE host (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    country_id BIGINT NOT NULL,
    CONSTRAINT fk_host_country
        FOREIGN KEY (country_id) REFERENCES country(id)
);

CREATE TABLE accommodation (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    name VARCHAR(200) NOT NULL,
    category VARCHAR(50) NOT NULL,
    condition VARCHAR(20) NOT NULL,
    rented BOOLEAN NOT NULL DEFAULT FALSE,
    host_id BIGINT NOT NULL,
    num_rooms INTEGER NOT NULL,
    CONSTRAINT fk_accommodation_host
        FOREIGN KEY (host_id) REFERENCES host(id)
);

INSERT INTO country (name, continent)
VALUES
    ('Macedonia', 'Europe'),
    ('Germany', 'Europe'),
    ('Italy', 'Europe'),
    ('France', 'Europe'),
    ('Spain', 'Europe');

INSERT INTO host (name, surname, country_id)
VALUES
    ('Bojan', 'Petrovski', 1),
    ('Angela', 'Muller', 2),
    ('Marco', 'Rossi', 3),
    ('Jean', 'Dupont', 4),
    ('Carlos', 'Garcia', 5);

INSERT INTO accommodation (name, category, condition, rented, host_id, num_rooms)
VALUES
    ('Skopje Central Apartment', 'APARTMENT', 'GOOD', FALSE, 1, 3),
    ('Berlin City Rooms', 'ROOM', 'GOOD', FALSE, 2, 2),
    ('Rome Sunset House', 'HOUSE', 'GOOD', FALSE, 3, 5),
    ('Paris Boutique Hotel', 'HOTEL', 'BAD', FALSE, 4, 20),
    ('Madrid Budget Motel', 'MOTEL', 'GOOD', TRUE, 5, 10);