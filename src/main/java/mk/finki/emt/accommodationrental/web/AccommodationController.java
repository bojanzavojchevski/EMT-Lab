package mk.finki.emt.accommodationrental.web;

import jakarta.validation.Valid;
import mk.finki.emt.accommodationrental.model.domain.Accommodation;
import mk.finki.emt.accommodationrental.model.dto.AccommodationDto;
import mk.finki.emt.accommodationrental.service.application.AccommodationApplicationService;
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
    public List<Accommodation> findAll() {
        return accommodationService.findAll();
    }

    @GetMapping("/filter")
    public List<Accommodation> findAllByRented(@RequestParam Boolean rented) {
        return accommodationService.findAllByRented(rented);
    }

    @GetMapping("/{id}")
    public Accommodation findById(@PathVariable Long id) {
        return accommodationService.findById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Accommodation> create(@Valid @RequestBody AccommodationDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accommodationService.create(dto));
    }

    @PutMapping("/{id}/edit")
    public Accommodation update(@PathVariable Long id,
                                @Valid @RequestBody AccommodationDto dto) {
        return accommodationService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accommodationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/rent")
    public Accommodation markAsRented(@PathVariable Long id) {
        return accommodationService.markAsRented(id);
    }
}