package mk.finki.emt.accommodationrental.service.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryStatsRefreshService {

    private final JdbcTemplate jdbcTemplate;

    public CategoryStatsRefreshService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Scheduled(fixedDelayString = "${app.stats.refresh-ms:60000}")
    @Transactional
    public void refreshCategoryStats() {
        jdbcTemplate.update("DELETE FROM accommodation_category_stats");

        jdbcTemplate.update("""
                INSERT INTO accommodation_category_stats (category, total_accommodations, total_rooms, average_rooms)
                SELECT a.category,
                       COUNT(*) AS total_accommodations,
                       SUM(a.num_rooms) AS total_rooms,
                       CAST(AVG(a.num_rooms) AS DECIMAL(19,2)) AS average_rooms
                FROM accommodation a
                GROUP BY a.category
                """);
    }
}

