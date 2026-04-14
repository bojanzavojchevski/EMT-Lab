package mk.finki.emt.accommodationrental.service.domain;

import mk.finki.emt.accommodationrental.repository.AccommodationAggregateRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccommodationAggregateRefreshService {

    private final AccommodationAggregateRepository accommodationAggregateRepository;

    public AccommodationAggregateRefreshService(AccommodationAggregateRepository accommodationAggregateRepository) {
        this.accommodationAggregateRepository = accommodationAggregateRepository;
    }

    @Transactional
    public void refreshAggregateTable() {
        accommodationAggregateRepository.clearAll();
        accommodationAggregateRepository.refillFromSourceTables();
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initializeAggregateTable() {
        refreshAggregateTable();
    }
}

