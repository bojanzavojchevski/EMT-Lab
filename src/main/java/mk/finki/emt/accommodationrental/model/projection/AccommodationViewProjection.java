package mk.finki.emt.accommodationrental.model.projection;

public interface AccommodationViewProjection {

    Long getAccommodationId();

    String getAccommodationName();

    String getCategory();

    Integer getNumRooms();

    String getHostFullName();

    String getCountryName();
}

