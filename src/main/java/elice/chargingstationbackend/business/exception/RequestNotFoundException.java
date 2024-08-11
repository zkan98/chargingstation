package elice.chargingstationbackend.business.exception;

public class RequestNotFoundException extends CustomException {
    public RequestNotFoundException(Long requestId) {
        super("Request not found with id " + requestId);
    }
}
