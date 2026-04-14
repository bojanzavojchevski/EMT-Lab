package mk.finki.emt.accommodationrental.repository;

import mk.finki.emt.accommodationrental.model.domain.AccommodationAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationAggregateRepository extends JpaRepository<AccommodationAggregate, Long> {

    @Modifying
    @Query(value = "DELETE FROM accommodation_aggregate", nativeQuery = true)
    void clearAll();

    @Modifying
    @Query(value = """
            INSERT INTO accommodation_aggregate (
                accommodation_id,
                accommodation_name,
                host_full_name,
                country_name,
                category,
                condition_value,
                available_rooms,
                fully_occupied,
                refreshed_at
            )
            SELECT a.id,
                   a.name,
                   CONCAT(h.name, ' ', h.surname),
                   c.name,
                   a.category,
                   a.condition,
                   a.num_rooms,
                   CASE WHEN a.num_rooms = 0 THEN TRUE ELSE FALSE END,
                   CURRENT_TIMESTAMP
            FROM accommodation a
            JOIN host h ON h.id = a.host_id
            JOIN country c ON c.id = h.country_id
            """, nativeQuery = true)
    void refillFromSourceTables();
}

