package mk.finki.emt.accommodationrental.model.projection;

import mk.finki.emt.accommodationrental.model.enums.Category;

public interface AccommodationShortProjection {

    Long getId();

    String getName();

    Category getCategory();

    Integer getNumRooms();
}

