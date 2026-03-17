package mk.finki.emt.accommodationrental.service.domain;

import mk.finki.emt.accommodationrental.model.domain.Accommodation;
import mk.finki.emt.accommodationrental.model.domain.Host;
import mk.finki.emt.accommodationrental.model.dto.AccommodationDto;
import mk.finki.emt.accommodationrental.repository.AccommodationRepository;
import mk.finki.emt.accommodationrental.repository.HostRepository;
import mk.finki.emt.accommodationrental.service.application.AccommodationApplicationService;
import mk.finki.emt.accommodationrental.web.handler.AccommodationNotFoundException;
import mk.finki.emt.accommodationrental.web.handler.HostNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccommodationApplicationServiceImpl implements AccommodationApplicationService {

    private final AccommodationRepository accommodationRepository;
    private final HostRepository hostRepository;

    public AccommodationApplicationServiceImpl(AccommodationRepository accommodationRepository,
                                               HostRepository hostRepository) {
        this.accommodationRepository = accommodationRepository;
        this.hostRepository = hostRepository;
    }

    @Override
    public List<Accommodation> findAll() {
        return accommodationRepository.findAll();
    }

    @Override
    public List<Accommodation> findAllByRented(Boolean rented) {
        return accommodationRepository.findAllByRented(rented);
    }

    @Override
    public Accommodation findById(Long id) {
        return accommodationRepository.findById(id)
                .orElseThrow(() -> new AccommodationNotFoundException(id));
    }

    @Override
    public Accommodation create(AccommodationDto dto) {
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

        return accommodationRepository.save(accommodation);
    }

    @Override
    public Accommodation update(Long id, AccommodationDto dto) {
        Accommodation accommodation = accommodationRepository.findById(id)
                .orElseThrow(() -> new AccommodationNotFoundException(id));

        Host host = hostRepository.findById(dto.getHostId())
                .orElseThrow(() -> new HostNotFoundException(dto.getHostId()));

        accommodation.setName(dto.getName());
        accommodation.setCategory(dto.getCategory());
        accommodation.setCondition(dto.getCondition());
        accommodation.setHost(host);
        accommodation.setNumRooms(dto.getNumRooms());
        accommodation.setUpdatedAt(LocalDateTime.now());

        return accommodationRepository.save(accommodation);
    }

    @Override
    public void delete(Long id) {
        Accommodation accommodation = accommodationRepository.findById(id)
                .orElseThrow(() -> new AccommodationNotFoundException(id));

        accommodationRepository.delete(accommodation);
    }

    @Override
    public Accommodation markAsRented(Long id) {
        Accommodation accommodation = accommodationRepository.findById(id)
                .orElseThrow(() -> new AccommodationNotFoundException(id));

        accommodation.setRented(true);
        accommodation.setUpdatedAt(LocalDateTime.now());

        return accommodationRepository.save(accommodation);
    }
}