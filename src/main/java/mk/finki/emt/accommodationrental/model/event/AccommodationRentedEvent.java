package mk.finki.emt.accommodationrental.model.event;

import java.time.LocalDateTime;

public class AccommodationRentedEvent {

    private final Long accommodationId;
    private final int remainingRooms;
    private final LocalDateTime occurredAt;

    public AccommodationRentedEvent(Long accommodationId, int remainingRooms, LocalDateTime occurredAt) {
        this.accommodationId = accommodationId;
        this.remainingRooms = remainingRooms;
        this.occurredAt = occurredAt;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public int getRemainingRooms() {
        return remainingRooms;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }
}

