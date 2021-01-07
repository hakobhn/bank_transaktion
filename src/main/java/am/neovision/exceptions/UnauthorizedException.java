package am.neovision.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author hakob.hakobyan created on 9/15/2020
 */
@NoArgsConstructor
@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Requested resource is restricted and requires authentication")
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
