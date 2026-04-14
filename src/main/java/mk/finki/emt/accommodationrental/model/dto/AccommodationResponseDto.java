package mk.finki.emt.accommodationrental.model.dto;

import mk.finki.emt.accommodationrental.model.domain.Accommodation;
import mk.finki.emt.accommodationrental.model.enums.Category;
import mk.finki.emt.accommodationrental.model.enums.Condition;

public class AccommodationResponseDto {

    private Long id;
    private String name;
    private Category category;
    private Condition condition;
    private Boolean rented;
    private Integer numRooms;
    private Long hostId;

    public AccommodationResponseDto() {
    }

    public AccommodationResponseDto(Long id, String name, Category category, Condition condition,
                                    Boolean rented, Integer numRooms, Long hostId) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.condition = condition;
        this.rented = rented;
        this.numRooms = numRooms;
        this.hostId = hostId;
    }

    public static AccommodationResponseDto from(Accommodation accommodation) {
        return new AccommodationResponseDto(
                accommodation.getId(),
                accommodation.getName(),
                accommodation.getCategory(),
                accommodation.getCondition(),
                accommodation.getRented(),
                accommodation.getNumRooms(),
                accommodation.getHost().getId()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public Condition getCondition() {
        return condition;
    }

    public Boolean getRented() {
        return rented;
    }

    public Integer getNumRooms() {
        return numRooms;
    }

    public Long getHostId() {
        return hostId;
    }
}

