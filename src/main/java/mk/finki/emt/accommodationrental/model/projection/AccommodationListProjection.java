package mk.finki.emt.accommodationrental.model.projection;

import mk.finki.emt.accommodationrental.model.enums.Category;
import mk.finki.emt.accommodationrental.model.enums.Condition;

public interface AccommodationListProjection {

    Long getId();

    String getName();

    Category getCategory();

    Condition getCondition();

    Boolean getRented();

    Integer getNumRooms();

    String getHostName();

    String getHostSurname();

    String getCountryName();
}

