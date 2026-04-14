DROP VIEW IF EXISTS accommodation_view;

CREATE VIEW accommodation_view AS
SELECT
    a.id AS accommodation_id,
    a.name AS accommodation_name,
    a.category AS category,
    a.num_rooms AS num_rooms,
    CONCAT(h.name, ' ', h.surname) AS host_full_name,
    c.name AS country_name
FROM accommodation a
JOIN host h ON h.id = a.host_id
JOIN country c ON c.id = h.country_id;

