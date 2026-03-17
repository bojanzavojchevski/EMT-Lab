package mk.finki.emt.accommodationrental.web.handler;

public class AccommodationNotFoundException extends RuntimeException {
    public AccommodationNotFoundException(Long id) {
        super("Accommodation with id " + id + " was not found.");
    }
}