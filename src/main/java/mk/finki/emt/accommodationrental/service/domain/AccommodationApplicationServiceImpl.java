package mk.finki.emt.accommodationrental.service.domain;

import mk.finki.emt.accommodationrental.model.domain.Accommodation;
import mk.finki.emt.accommodationrental.model.domain.Host;
import mk.finki.emt.accommodationrental.model.dto.AccommodationEntityGraphDto;
import mk.finki.emt.accommodationrental.model.dto.AccommodationListDto;
import mk.finki.emt.accommodationrental.model.dto.ActivityLogDto;
import mk.finki.emt.accommodationrental.model.dto.AccommodationDto;
import mk.finki.emt.accommodationrental.model.dto.AccommodationResponseDto;
import mk.finki.emt.accommodationrental.model.enums.Category;
import mk.finki.emt.accommodationrental.model.event.AccommodationRentedEvent;
import mk.finki.emt.accommodationrental.model.projection.AccommodationExtendedProjection;
import mk.finki.emt.accommodationrental.model.projection.AccommodationShortProjection;
import mk.finki.emt.accommodationrental.model.projection.AccommodationViewProjection;
import mk.finki.emt.accommodationrental.model.projection.CategoryStatsProjection;
import mk.finki.emt.accommodationrental.repository.AccommodationRepository;
import mk.finki.emt.accommodationrental.repository.ActivityLogRepository;
import mk.finki.emt.accommodationrental.repository.HostRepository;
import mk.finki.emt.accommodationrental.service.application.AccommodationApplicationService;
import mk.finki.emt.accommodationrental.web.handler.AccommodationNotFoundException;
import mk.finki.emt.accommodationrental.web.handler.HostNotFoundException;
import mk.finki.emt.accommodationrental.web.handler.NoAvailableRoomsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class AccommodationApplicationServiceImpl implements AccommodationApplicationService {

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("name", "createdAt");

    private final AccommodationRepository accommodationRepository;
    private final ActivityLogRepository activityLogRepository;
    private final HostRepository hostRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final AccommodationAggregateRefreshService accommodationAggregateRefreshService;

    public AccommodationApplicationServiceImpl(AccommodationRepository accommodationRepository,
                                               ActivityLogRepository activityLogRepository,
                                               HostRepository hostRepository,
                                               ApplicationEventPublisher applicationEventPublisher,
                                               AccommodationAggregateRefreshService accommodationAggregateRefreshService) {
        this.accommodationRepository = accommodationRepository;
        this.activityLogRepository = activityLogRepository;
        this.hostRepository = hostRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.accommodationAggregateRefreshService = accommodationAggregateRefreshService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccommodationListDto> findAll() {
        return accommodationRepository.findAllProjected()
                .stream()
                .map(AccommodationListDto::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccommodationListDto> findAllByRented(Boolean rented) {
        return accommodationRepository.findAllProjectedByRented(rented)
                .stream()
                .map(AccommodationListDto::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AccommodationResponseDto findById(Long id) {
        return AccommodationResponseDto.from(getAccommodationByIdWithHostAndCountry(id));
    }

    @Override
    @Transactional
    public AccommodationResponseDto create(AccommodationDto dto) {
        Host host = hostRepository.findById(dto.getHostId())
                .orElseThrow(() -> new HostNotFoundException(dto.getHostId()));

        Accommodation accommodation = new Accommodation(
                dto.getName(),
                dto.getCategory(),
                dto.getCondition(),
                false,
                host,
                dto.getNumRooms()
        );

        Accommodation savedAccommodation = accommodationRepository.save(accommodation);
        accommodationAggregateRefreshService.refreshAggregateTable();
        return AccommodationResponseDto.from(savedAccommodation);
    }

    @Override
    @Transactional
    public AccommodationResponseDto update(Long id, AccommodationDto dto) {
        Accommodation accommodation = getAccommodationById(id);

        Host host = hostRepository.findById(dto.getHostId())
                .orElseThrow(() -> new HostNotFoundException(dto.getHostId()));

        accommodation.setName(dto.getName());
        accommodation.setCategory(dto.getCategory());
        accommodation.setCondition(dto.getCondition());
        accommodation.setHost(host);
        accommodation.setNumRooms(dto.getNumRooms());
        accommodation.setUpdatedAt(LocalDateTime.now());

        Accommodation savedAccommodation = accommodationRepository.save(accommodation);
        accommodationAggregateRefreshService.refreshAggregateTable();
        return AccommodationResponseDto.from(savedAccommodation);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Accommodation accommodation = getAccommodationById(id);

        accommodationRepository.delete(accommodation);
        accommodationAggregateRefreshService.refreshAggregateTable();
    }

    @Override
    @Transactional
    public AccommodationResponseDto markAsRented(Long id) {
        Accommodation accommodation = getAccommodationById(id);

        if (accommodation.getNumRooms() == null || accommodation.getNumRooms() <= 0) {
            throw new NoAvailableRoomsException(id);
        }

        int remainingRooms = accommodation.rentOneRoom();
        Accommodation savedAccommodation = accommodationRepository.save(accommodation);

        applicationEventPublisher.publishEvent(new AccommodationRentedEvent(
                savedAccommodation.getId(),
                remainingRooms,
                LocalDateTime.now()
        ));

        return AccommodationResponseDto.from(savedAccommodation);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccommodationListDto> search(Integer page,
                                             Integer size,
                                             String sortBy,
                                             String sortDirection,
                                             Category category,
                                             Long hostId,
                                             Long countryId,
                                             Integer numRooms,
                                             Boolean hasFreeRooms) {
        Pageable pageable = buildPageable(page, size, sortBy, sortDirection);

        return accommodationRepository.searchExtended(category, hostId, countryId, numRooms, hasFreeRooms, pageable)
                .map(AccommodationListDto::from);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccommodationShortProjection> searchShortProjection(Integer page,
                                                                    Integer size,
                                                                    String sortBy,
                                                                    String sortDirection,
                                                                    Category category,
                                                                    Long hostId,
                                                                    Long countryId,
                                                                    Integer numRooms,
                                                                    Boolean hasFreeRooms) {
        Pageable pageable = buildPageable(page, size, sortBy, sortDirection);
        return accommodationRepository.searchShort(category, hostId, countryId, numRooms, hasFreeRooms, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccommodationExtendedProjection> searchExtendedProjection(Integer page,
                                                                          Integer size,
                                                                          String sortBy,
                                                                          String sortDirection,
                                                                          Category category,
                                                                          Long hostId,
                                                                          Long countryId,
                                                                          Integer numRooms,
                                                                          Boolean hasFreeRooms) {
        Pageable pageable = buildPageable(page, size, sortBy, sortDirection);
        return accommodationRepository.searchExtended(category, hostId, countryId, numRooms, hasFreeRooms, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccommodationViewProjection> findAllFromView() {
        return accommodationRepository.findAllFromAccommodationView();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryStatsProjection> findCategoryStats() {
        return accommodationRepository.findCategoryStats();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityLogDto> findActivityLogs(Integer page, Integer size) {
        int resolvedPage = page == null || page < 0 ? 0 : page;
        int resolvedSize = size == null || size <= 0 ? 10 : size;

        Pageable pageable = PageRequest.of(resolvedPage, resolvedSize);
        return activityLogRepository.findAllByOrderByEventTimeDesc(pageable)
                .map(ActivityLogDto::from);
    }

    @Override
    @Transactional(readOnly = true)
    public AccommodationEntityGraphDto findByIdEntityGraph(Long id) {
        return AccommodationEntityGraphDto.from(getAccommodationByIdWithHostAndCountry(id));
    }

    private Accommodation getAccommodationById(Long id) {
        return accommodationRepository.findById(id)
                .orElseThrow(() -> new AccommodationNotFoundException(id));
    }

    private Accommodation getAccommodationByIdWithHostAndCountry(Long id) {
        return accommodationRepository.findAccommodationById(id)
                .orElseThrow(() -> new AccommodationNotFoundException(id));
    }

    private Pageable buildPageable(Integer page, Integer size, String sortBy, String sortDirection) {
        String resolvedSortBy = sortBy == null ? "createdAt" : sortBy;
        if (!ALLOWED_SORT_FIELDS.contains(resolvedSortBy)) {
            throw new IllegalArgumentException("Allowed sort fields are: name, createdAt.");
        }

        String resolvedDirection = sortDirection == null ? "desc" : sortDirection;
        Sort.Direction direction = "asc".equalsIgnoreCase(resolvedDirection)
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        int resolvedPage = page == null || page < 0 ? 0 : page;
        int resolvedSize = size == null || size <= 0 ? 10 : size;

        return PageRequest.of(resolvedPage, resolvedSize, Sort.by(direction, resolvedSortBy));
    }
}