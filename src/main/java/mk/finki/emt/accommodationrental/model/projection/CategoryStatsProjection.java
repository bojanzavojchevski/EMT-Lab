package mk.finki.emt.accommodationrental.model.projection;

import java.math.BigDecimal;

public interface CategoryStatsProjection {

    String getCategory();

    Long getTotalAccommodations();

    Integer getTotalRooms();

    BigDecimal getAverageRooms();
}

