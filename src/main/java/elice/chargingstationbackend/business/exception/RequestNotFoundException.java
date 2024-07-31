package elice.chargingstationbackend.business.exception;

public class RequestNotFoundException extends RuntimeException {
    public RequestNotFoundException(Long requestId) {
        super("Request not found with id " + requestId);
    }
}
