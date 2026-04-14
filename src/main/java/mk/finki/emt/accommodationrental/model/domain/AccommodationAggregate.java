package mk.finki.emt.accommodationrental.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "accommodation_aggregate")
public class AccommodationAggregate {

    @Id
    @Column(name = "accommodation_id")
    private Long accommodationId;

    @Column(name = "accommodation_name", nullable = false)
    private String accommodationName;

    @Column(name = "host_full_name", nullable = false)
    private String hostFullName;

    @Column(name = "country_name", nullable = false)
    private String countryName;

    @Column(nullable = false)
    private String category;

    @Column(name = "condition_value", nullable = false)
    private String conditionValue;

    @Column(name = "available_rooms", nullable = false)
    private Integer availableRooms;

    @Column(name = "fully_occupied", nullable = false)
    private Boolean fullyOccupied;

    @Column(name = "refreshed_at", nullable = false)
    private LocalDateTime refreshedAt;

    public AccommodationAggregate() {
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public String getAccommodationName() {
        return accommodationName;
    }

    public String getHostFullName() {
        return hostFullName;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCategory() {
        return category;
    }

    public String getConditionValue() {
        return conditionValue;
    }

    public Integer getAvailableRooms() {
        return availableRooms;
    }

    public Boolean getFullyOccupied() {
        return fullyOccupied;
    }

    public LocalDateTime getRefreshedAt() {
        return refreshedAt;
    }
}

