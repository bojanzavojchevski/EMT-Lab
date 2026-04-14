package mk.finki.emt.accommodationrental.model.dto;

import mk.finki.emt.accommodationrental.model.enums.Category;
import mk.finki.emt.accommodationrental.model.enums.Condition;
import mk.finki.emt.accommodationrental.model.projection.AccommodationExtendedProjection;
import mk.finki.emt.accommodationrental.model.projection.AccommodationListProjection;

public class AccommodationListDto {

    private Long id;
    private String name;
    private Category category;
    private Condition condition;
    private Boolean rented;
    private Integer numRooms;
    private String hostFullName;
    private String countryName;

    public AccommodationListDto() {
    }

    public AccommodationListDto(Long id, String name, Category category, Condition condition,
                                Boolean rented, Integer numRooms, String hostFullName, String countryName) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.condition = condition;
        this.rented = rented;
        this.numRooms = numRooms;
        this.hostFullName = hostFullName;
        this.countryName = countryName;
    }

    public static AccommodationListDto from(AccommodationListProjection projection) {
        return new AccommodationListDto(
                projection.getId(),
                projection.getName(),
                projection.getCategory(),
                projection.getCondition(),
                projection.getRented(),
                projection.getNumRooms(),
                projection.getHostName() + " " + projection.getHostSurname(),
                projection.getCountryName()
        );
    }

    public static AccommodationListDto from(AccommodationExtendedProjection projection) {
        return new AccommodationListDto(
                projection.getId(),
                projection.getName(),
                projection.getCategory(),
                null,
                null,
                projection.getNumRooms(),
                projection.getHostFullName(),
                projection.getCountryName()
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

    public String getHostFullName() {
        return hostFullName;
    }

    public String getCountryName() {
        return countryName;
    }
}

