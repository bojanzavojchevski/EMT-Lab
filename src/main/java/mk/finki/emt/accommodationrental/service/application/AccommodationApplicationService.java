package mk.finki.emt.accommodationrental.service.application;

import mk.finki.emt.accommodationrental.model.domain.Accommodation;
import mk.finki.emt.accommodationrental.model.dto.AccommodationDto;

import java.util.List;

public interface AccommodationApplicationService {

    List<Accommodation> findAll();

    List<Accommodation> findAllByRented(Boolean rented);

    Accommodation findById(Long id);

    Accommodation create(AccommodationDto dto);

    Accommodation update(Long id, AccommodationDto dto);

    void delete(Long id);

    Accommodation markAsRented(Long id);
}