package am.neovision.config;

/**
 * @author hakob.hakobyan created on 12/4/2020
 */

import am.neovision.exceptions.NotFoundException;
import am.neovision.exceptions.PermissionDenied;
import am.neovision.exceptions.UnauthorizedException;
import am.neovision.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ApiResponse> handleNotFound(Exception ex, WebRequest request) {
        return new ResponseEntity(new ApiResponse(false, "Requested resource could not be found",
                ex.getLocalizedMessage(), 404, null),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<ApiResponse> handleUnauthorized(Exception ex, WebRequest request) {
        return new ResponseEntity(new ApiResponse(false, "Requested resource is restricted and requires authentication",
                ex.getLocalizedMessage(), 401, null),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({PermissionDenied.class})
    public ResponseEntity<ApiResponse> handlePermissionDenied(Exception ex, WebRequest request) {
        return new ResponseEntity(new ApiResponse(false, "You don't have permission to access the resource",
                ex.getLocalizedMessage(), 403, null),
                HttpStatus.FORBIDDEN);
    }
}
