package am.neovision.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author hakob.hakobyan created on 9/1/2020
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "entity forbidden")
public class PermissionDenied extends RuntimeException {

    public PermissionDenied(String message) {
        super(message);
    }

    public PermissionDenied(String message, Throwable cause) {
        super(message, cause);
    }
}