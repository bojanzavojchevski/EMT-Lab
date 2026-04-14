package mk.finki.emt.accommodationrental.model.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "activity_log")
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(name = "accommodation_name", nullable = false)
    private String accommodationName;

    @Column(name = "event_time", nullable = false)
    private LocalDateTime eventTime;

    public ActivityLog() {
    }

    public ActivityLog(String accommodationName, LocalDateTime eventTime, String eventType) {
        this.accommodationName = accommodationName;
        this.eventTime = eventTime;
        this.eventType = eventType;
    }

    public Long getId() {
        return id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getAccommodationName() {
        return accommodationName;
    }

    public void setAccommodationName(String accommodationName) {
        this.accommodationName = accommodationName;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }
}

