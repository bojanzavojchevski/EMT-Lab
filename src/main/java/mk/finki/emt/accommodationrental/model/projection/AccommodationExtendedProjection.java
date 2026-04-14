package mk.finki.emt.accommodationrental.model.projection;

import mk.finki.emt.accommodationrental.model.enums.Category;

public interface AccommodationExtendedProjection {

    Long getId();

    String getName();

    Category getCategory();

    Integer getNumRooms();

    String getHostFullName();

    String getCountryName();
}

