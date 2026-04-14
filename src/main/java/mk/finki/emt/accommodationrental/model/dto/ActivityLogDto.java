package mk.finki.emt.accommodationrental.model.dto;

import mk.finki.emt.accommodationrental.model.domain.ActivityLog;

import java.time.LocalDateTime;

public class ActivityLogDto {

    private Long id;
    private String accommodationName;
    private LocalDateTime eventTime;
    private String eventType;

    public ActivityLogDto() {
    }

    public ActivityLogDto(Long id, String accommodationName, LocalDateTime eventTime, String eventType) {
        this.id = id;
        this.accommodationName = accommodationName;
        this.eventTime = eventTime;
        this.eventType = eventType;
    }

    public static ActivityLogDto from(ActivityLog activityLog) {
        return new ActivityLogDto(
                activityLog.getId(),
                activityLog.getAccommodationName(),
                activityLog.getEventTime(),
                activityLog.getEventType()
        );
    }

    public Long getId() {
        return id;
    }

    public String getAccommodationName() {
        return accommodationName;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public String getEventType() {
        return eventType;
    }
}

