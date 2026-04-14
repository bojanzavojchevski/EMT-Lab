package mk.finki.emt.accommodationrental.service.application;

import mk.finki.emt.accommodationrental.model.dto.AccommodationListDto;
import mk.finki.emt.accommodationrental.model.dto.AccommodationEntityGraphDto;
import mk.finki.emt.accommodationrental.model.dto.ActivityLogDto;
import mk.finki.emt.accommodationrental.model.dto.AccommodationDto;
import mk.finki.emt.accommodationrental.model.dto.AccommodationResponseDto;
import mk.finki.emt.accommodationrental.model.enums.Category;
import mk.finki.emt.accommodationrental.model.projection.AccommodationExtendedProjection;
import mk.finki.emt.accommodationrental.model.projection.AccommodationShortProjection;
import mk.finki.emt.accommodationrental.model.projection.AccommodationViewProjection;
import mk.finki.emt.accommodationrental.model.projection.CategoryStatsProjection;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccommodationApplicationService {

    List<AccommodationListDto> findAll();

    List<AccommodationListDto> findAllByRented(Boolean rented);

    AccommodationResponseDto findById(Long id);

    AccommodationResponseDto create(AccommodationDto dto);

    AccommodationResponseDto update(Long id, AccommodationDto dto);

    void delete(Long id);

    AccommodationResponseDto markAsRented(Long id);

    Page<AccommodationListDto> search(Integer page,
                                      Integer size,
                                      String sortBy,
                                      String sortDirection,
                                      Category category,
                                      Long hostId,
                                      Long countryId,
                                      Integer numRooms,
                                      Boolean hasFreeRooms);

    Page<AccommodationShortProjection> searchShortProjection(Integer page,
                                                             Integer size,
                                                             String sortBy,
                                                             String sortDirection,
                                                             Category category,
                                                             Long hostId,
                                                             Long countryId,
                                                             Integer numRooms,
                                                             Boolean hasFreeRooms);

    Page<AccommodationExtendedProjection> searchExtendedProjection(Integer page,
                                                                  Integer size,
                                                                  String sortBy,
                                                                  String sortDirection,
                                                                  Category category,
                                                                  Long hostId,
                                                                  Long countryId,
                                                                  Integer numRooms,
                                                                  Boolean hasFreeRooms);

    List<AccommodationViewProjection> findAllFromView();

    List<CategoryStatsProjection> findCategoryStats();

    Page<ActivityLogDto> findActivityLogs(Integer page, Integer size);

    AccommodationEntityGraphDto findByIdEntityGraph(Long id);
}