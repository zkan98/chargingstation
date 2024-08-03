package elice.chargingstationbackend.business.exception;

public class BusinessOwnerNotFoundException extends RuntimeException {
    public BusinessOwnerNotFoundException(Long ownerId) {
        super("BusinessOwner not found with id " + ownerId);
    }
}
