package mk.finki.emt.accommodationrental.model.dto;

import mk.finki.emt.accommodationrental.model.domain.Accommodation;
import mk.finki.emt.accommodationrental.model.domain.Host;
import mk.finki.emt.accommodationrental.model.enums.Category;

public class AccommodationEntityGraphDto {

    private Long id;
    private String name;
    private Category category;
    private Integer numRooms;
    private String hostFullName;
    private String countryName;

    public AccommodationEntityGraphDto() {
    }

    public AccommodationEntityGraphDto(Long id,
                                       String name,
                                       Category category,
                                       Integer numRooms,
                                       String hostFullName,
                                       String countryName) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.numRooms = numRooms;
        this.hostFullName = hostFullName;
        this.countryName = countryName;
    }

    public static AccommodationEntityGraphDto from(Accommodation accommodation) {
        Host host = accommodation.getHost();
        String hostFullName = host.getName() + " " + host.getSurname();

        return new AccommodationEntityGraphDto(
                accommodation.getId(),
                accommodation.getName(),
                accommodation.getCategory(),
                accommodation.getNumRooms(),
                hostFullName,
                host.getCountry().getName()
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
