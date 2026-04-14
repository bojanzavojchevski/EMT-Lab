package mk.finki.emt.accommodationrental.web.handler;

public class NoAvailableRoomsException extends RuntimeException {

    public NoAvailableRoomsException(Long accommodationId) {
        super("Accommodation with id " + accommodationId + " has no available rooms.");
    }
}

