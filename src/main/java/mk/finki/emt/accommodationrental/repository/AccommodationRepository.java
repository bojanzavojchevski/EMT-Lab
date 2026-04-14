package mk.finki.emt.accommodationrental.repository;

import mk.finki.emt.accommodationrental.model.domain.Accommodation;
import mk.finki.emt.accommodationrental.model.enums.Category;
import mk.finki.emt.accommodationrental.model.projection.AccommodationListProjection;
import mk.finki.emt.accommodationrental.model.projection.AccommodationExtendedProjection;
import mk.finki.emt.accommodationrental.model.projection.AccommodationShortProjection;
import mk.finki.emt.accommodationrental.model.projection.AccommodationViewProjection;
import mk.finki.emt.accommodationrental.model.projection.CategoryStatsProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    @EntityGraph(attributePaths = {"host", "host.country"})
    Optional<Accommodation> findAccommodationById(Long id);

    List<Accommodation> findAllByRented(Boolean rented);

    @Query("""
            select a.id as id,
                   a.name as name,
                   a.category as category,
                   a.condition as condition,
                   a.rented as rented,
                   a.numRooms as numRooms,
                   h.name as hostName,
                   h.surname as hostSurname,
                   c.name as countryName
            from Accommodation a
            join a.host h
            join h.country c
            """)
    List<AccommodationListProjection> findAllProjected();

    @Query("""
            select a.id as id,
                   a.name as name,
                   a.category as category,
                   a.condition as condition,
                   a.rented as rented,
                   a.numRooms as numRooms,
                   h.name as hostName,
                   h.surname as hostSurname,
                   c.name as countryName
            from Accommodation a
            join a.host h
            join h.country c
            where a.rented = :rented
            """)
    List<AccommodationListProjection> findAllProjectedByRented(Boolean rented);

    @Query("""
            select a.id as id,
                   a.name as name,
                   a.category as category,
                   a.numRooms as numRooms
            from Accommodation a
            join a.host h
            join h.country c
            where (:category is null or a.category = :category)
              and (:hostId is null or h.id = :hostId)
              and (:countryId is null or c.id = :countryId)
              and (:numRooms is null or a.numRooms = :numRooms)
              and (
                    :hasFreeRooms is null
                    or (:hasFreeRooms = true and a.numRooms > 0)
                    or (:hasFreeRooms = false and a.numRooms = 0)
              )
            """)
    Page<AccommodationShortProjection> searchShort(
            @Param("category") Category category,
            @Param("hostId") Long hostId,
            @Param("countryId") Long countryId,
            @Param("numRooms") Integer numRooms,
            @Param("hasFreeRooms") Boolean hasFreeRooms,
            Pageable pageable
    );

    @Query("""
            select a.id as id,
                   a.name as name,
                   a.category as category,
                   a.numRooms as numRooms,
                   concat(h.name, ' ', h.surname) as hostFullName,
                   c.name as countryName
            from Accommodation a
            join a.host h
            join h.country c
            where (:category is null or a.category = :category)
              and (:hostId is null or h.id = :hostId)
              and (:countryId is null or c.id = :countryId)
              and (:numRooms is null or a.numRooms = :numRooms)
              and (
                    :hasFreeRooms is null
                    or (:hasFreeRooms = true and a.numRooms > 0)
                    or (:hasFreeRooms = false and a.numRooms = 0)
              )
            """)
    Page<AccommodationExtendedProjection> searchExtended(
            @Param("category") Category category,
            @Param("hostId") Long hostId,
            @Param("countryId") Long countryId,
            @Param("numRooms") Integer numRooms,
            @Param("hasFreeRooms") Boolean hasFreeRooms,
            Pageable pageable
    );

    @Query(value = """
            SELECT accommodation_id AS accommodationId,
                   accommodation_name AS accommodationName,
                   category AS category,
                   num_rooms AS numRooms,
                   host_full_name AS hostFullName,
                   country_name AS countryName
            FROM accommodation_view
            """, nativeQuery = true)
    List<AccommodationViewProjection> findAllFromAccommodationView();

    @Query(value = """
            SELECT category AS category,
                   total_accommodations AS totalAccommodations,
                   total_rooms AS totalRooms,
                   average_rooms AS averageRooms
            FROM accommodation_category_stats
            ORDER BY category
            """, nativeQuery = true)
    List<CategoryStatsProjection> findCategoryStats();
}