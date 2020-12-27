package am.neovision.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author hakob.hakobyan created on 9/15/2020
 */
@NoArgsConstructor
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "entity not found")
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
