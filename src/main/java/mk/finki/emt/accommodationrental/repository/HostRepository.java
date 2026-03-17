package mk.finki.emt.accommodationrental.repository;

import mk.finki.emt.accommodationrental.model.domain.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostRepository extends JpaRepository<Host, Long> {
}