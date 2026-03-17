package mk.finki.emt.accommodationrental.repository;

import mk.finki.emt.accommodationrental.model.domain.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    List<Accommodation> findAllByRented(Boolean rented);
}