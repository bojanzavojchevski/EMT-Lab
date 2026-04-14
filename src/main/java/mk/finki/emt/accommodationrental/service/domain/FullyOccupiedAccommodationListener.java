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
public class FullyOccupiedAccommodationListener {

    private final AccommodationRepository accommodationRepository;
    private final ActivityLogRepository activityLogRepository;

    public FullyOccupiedAccommodationListener(AccommodationRepository accommodationRepository,
                                              ActivityLogRepository activityLogRepository) {
        this.accommodationRepository = accommodationRepository;
        this.activityLogRepository = activityLogRepository;
    }

    @EventListener
    @Transactional
    public void onAccommodationRented(AccommodationRentedEvent event) {
        if (event.getRemainingRooms() > 0) {
            return;
        }

        Accommodation accommodation = accommodationRepository.findById(event.getAccommodationId())
                .orElseThrow(() -> new AccommodationNotFoundException(event.getAccommodationId()));

        if (!accommodation.isFullyOccupied()) {
            return;
        }

        activityLogRepository.save(new ActivityLog(
                accommodation.getName(),
                event.getOccurredAt(),
                "ACCOMMODATION_FULLY_OCCUPIED"
        ));
    }
}

