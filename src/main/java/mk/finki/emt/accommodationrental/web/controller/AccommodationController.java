package mk.finki.emt.accommodationrental.web.controller;

import jakarta.validation.Valid;
import mk.finki.emt.accommodationrental.model.dto.AccommodationEntityGraphDto;
import mk.finki.emt.accommodationrental.model.dto.AccommodationListDto;
import mk.finki.emt.accommodationrental.model.dto.ActivityLogDto;
import mk.finki.emt.accommodationrental.model.dto.AccommodationDto;
import mk.finki.emt.accommodationrental.model.dto.AccommodationResponseDto;
import mk.finki.emt.accommodationrental.model.enums.Category;
import mk.finki.emt.accommodationrental.model.projection.AccommodationExtendedProjection;
import mk.finki.emt.accommodationrental.model.projection.AccommodationShortProjection;
import mk.finki.emt.accommodationrental.model.projection.AccommodationViewProjection;
import mk.finki.emt.accommodationrental.model.projection.CategoryStatsProjection;
import mk.finki.emt.accommodationrental.service.application.AccommodationApplicationService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accommodations")
public class AccommodationController {

    private final AccommodationApplicationService accommodationService;

    public AccommodationController(AccommodationApplicationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @GetMapping
    public List<AccommodationListDto> findAll() {
        return accommodationService.findAll();
    }

    @GetMapping("/filter")
    public List<AccommodationListDto> findAllByRented(@RequestParam Boolean rented) {
        return accommodationService.findAllByRented(rented);
    }

    @GetMapping("/search")
    public Page<AccommodationListDto> search(@RequestParam(defaultValue = "0") Integer page,
                                             @RequestParam(defaultValue = "10") Integer size,
                                             @RequestParam(defaultValue = "createdAt") String sortBy,
                                             @RequestParam(defaultValue = "desc") String sortDirection,
                                             @RequestParam(required = false) Category category,
                                             @RequestParam(required = false) Long hostId,
                                             @RequestParam(required = false) Long countryId,
                                             @RequestParam(required = false) Integer numRooms,
                                             @RequestParam(required = false) Boolean hasFreeRooms) {
        return accommodationService.search(page, size, sortBy, sortDirection, category, hostId, countryId, numRooms, hasFreeRooms);
    }

    @GetMapping("/search/short-projection")
    public Page<AccommodationShortProjection> searchShortProjection(@RequestParam(defaultValue = "0") Integer page,
                                                                    @RequestParam(defaultValue = "10") Integer size,
                                                                    @RequestParam(defaultValue = "createdAt") String sortBy,
                                                                    @RequestParam(defaultValue = "desc") String sortDirection,
                                                                    @RequestParam(required = false) Category category,
                                                                    @RequestParam(required = false) Long hostId,
                                                                    @RequestParam(required = false) Long countryId,
                                                                    @RequestParam(required = false) Integer numRooms,
                                                                    @RequestParam(required = false) Boolean hasFreeRooms) {
        return accommodationService.searchShortProjection(page, size, sortBy, sortDirection, category, hostId, countryId, numRooms, hasFreeRooms);
    }

    @GetMapping("/search/extended-projection")
    public Page<AccommodationExtendedProjection> searchExtendedProjection(@RequestParam(defaultValue = "0") Integer page,
                                                                          @RequestParam(defaultValue = "10") Integer size,
                                                                          @RequestParam(defaultValue = "createdAt") String sortBy,
                                                                          @RequestParam(defaultValue = "desc") String sortDirection,
                                                                          @RequestParam(required = false) Category category,
                                                                          @RequestParam(required = false) Long hostId,
                                                                          @RequestParam(required = false) Long countryId,
                                                                          @RequestParam(required = false) Integer numRooms,
                                                                          @RequestParam(required = false) Boolean hasFreeRooms) {
        return accommodationService.searchExtendedProjection(page, size, sortBy, sortDirection, category, hostId, countryId, numRooms, hasFreeRooms);
    }

    @GetMapping("/view")
    public List<AccommodationViewProjection> findAllFromView() {
        return accommodationService.findAllFromView();
    }

    @GetMapping("/stats/categories")
    public List<CategoryStatsProjection> findCategoryStats() {
        return accommodationService.findCategoryStats();
    }

    @GetMapping("/activity-logs")
    public Page<ActivityLogDto> findActivityLogs(@RequestParam(defaultValue = "0") Integer page,
                                                 @RequestParam(defaultValue = "10") Integer size) {
        return accommodationService.findActivityLogs(page, size);
    }

    @GetMapping("/{id}")
    public AccommodationResponseDto findById(@PathVariable Long id) {
        return accommodationService.findById(id);
    }

    @GetMapping("/{id}/entity-graph")
    public AccommodationEntityGraphDto findByIdEntityGraph(@PathVariable Long id) {
        return accommodationService.findByIdEntityGraph(id);
    }

    @PostMapping("/add")
    public ResponseEntity<AccommodationResponseDto> create(@Valid @RequestBody AccommodationDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accommodationService.create(dto));
    }

    @PutMapping("/{id}/edit")
    public AccommodationResponseDto update(@PathVariable Long id,
                                           @Valid @RequestBody AccommodationDto dto) {
        return accommodationService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accommodationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/rent")
    public AccommodationResponseDto markAsRented(@PathVariable Long id) {
        return accommodationService.markAsRented(id);
    }
}