package elice.chargingstationbackend.charger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ChargerNotFoundException extends RuntimeException {
    public ChargerNotFoundException() {
        super();
    }

    public ChargerNotFoundException(String message) {
        super(message);
    }

    public ChargerNotFoundException(Throwable cause) {
        super(cause);
    }

    public ChargerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
