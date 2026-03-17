package mk.finki.emt.accommodationrental.web.handler;

public class HostNotFoundException extends RuntimeException {
    public HostNotFoundException(Long id) {
        super("Host with id " + id + " was not found.");
    }
}