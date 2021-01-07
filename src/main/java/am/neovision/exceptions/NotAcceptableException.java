package am.neovision.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author hakob.hakobyan created on 9/15/2020
 */
@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Requested resource does not have a acceptable representation")
public class NotAcceptableException extends RuntimeException {
    public NotAcceptableException(String message) {
        super(message);
    }

    public NotAcceptableException(String message, Throwable cause) {
        super(message, cause);
    }
}
