CREATE TABLE accommodation_category_stats (
    category VARCHAR(50) PRIMARY KEY,
    total_accommodations BIGINT NOT NULL,
    total_rooms INTEGER NOT NULL,
    average_rooms DECIMAL(19,2) NOT NULL
);

INSERT INTO accommodation_category_stats (category, total_accommodations, total_rooms, average_rooms)
SELECT
    a.category,
    COUNT(*) AS total_accommodations,
    SUM(a.num_rooms) AS total_rooms,
    CAST(AVG(a.num_rooms) AS DECIMAL(19,2)) AS average_rooms
FROM accommodation a
GROUP BY a.category;

