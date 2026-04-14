package mk.finki.emt.accommodationrental.service.domain;

import mk.finki.emt.accommodationrental.model.domain.Accommodation;
import mk.finki.emt.accommodationrental.model.domain.ActivityLog;
import mk.finki.emt.accommodationrental.model.event.AccommodationRentedEvent;
import mk.finki.emt.accommodationrental.repository.AccommodationRepository;
import mk.finki.emt.accommodationrental.repository.ActivityLogRepository;
import mk.finki.emt.accommodationrental.web.handler.AccommodationNotFoundException;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RentActivityLogListener {

    private final AccommodationRepository accommodationRepository;
    private final ActivityLogRepository activityLogRepository;
    private final AccommodationAggregateRefreshService accommodationAggregateRefreshService;

    public RentActivityLogListener(AccommodationRepository accommodationRepository,
                                   ActivityLogRepository activityLogRepository,
                                   AccommodationAggregateRefreshService accommodationAggregateRefreshService) {
        this.accommodationRepository = accommodationRepository;
        this.activityLogRepository = activityLogRepository;
        this.accommodationAggregateRefreshService = accommodationAggregateRefreshService;
    }

    @EventListener
    @Transactional
    public void onAccommodationRented(AccommodationRentedEvent event) {
        Accommodation accommodation = accommodationRepository.findById(event.getAccommodationId())
                .orElseThrow(() -> new AccommodationNotFoundException(event.getAccommodationId()));

        activityLogRepository.save(new ActivityLog(
                accommodation.getName(),
                event.getOccurredAt(),
                "ACCOMMODATION_RENTED"
        ));

        accommodationAggregateRefreshService.refreshAggregateTable();
    }
}

