package elice.chargingstationbackend.business.exception;

public class BusinessOwnerNotFoundException extends CustomException {
    public BusinessOwnerNotFoundException(Long ownerId) {
        super("BusinessOwner not found with id " + ownerId);
    }
}
