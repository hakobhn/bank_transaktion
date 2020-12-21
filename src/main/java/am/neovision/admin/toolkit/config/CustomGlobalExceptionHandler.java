package am.neovision.admin.toolkit.config;

/**
 * @author hakob.hakobyan created on 12/4/2020
 */

import am.neovision.admin.toolkit.exceptions.NotFoundException;
import am.neovision.admin.toolkit.exceptions.PermissionDenied;
import am.neovision.admin.toolkit.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler({NotFoundException.class})
//    public ResponseEntity<ApiResponse> handleNotFound(Exception ex, WebRequest request) {
//        return new ResponseEntity(new ApiResponse(false, ex.getLocalizedMessage()),
//                HttpStatus.NOT_FOUND);
//    }

//    @ExceptionHandler({PermissionDenied.class})
//    public ResponseEntity<ApiResponse> handlePermissionDenied(Exception ex, WebRequest request) {
//        return new ResponseEntity(new ApiResponse(false, ex.getLocalizedMessage()),
//                HttpStatus.FORBIDDEN);
//    }

//    @ExceptionHandler({RuntimeException.class})
//    public ResponseEntity<ApiResponse> handleProcessFailed(Exception ex, WebRequest request) {
//        return new ResponseEntity(new ApiResponse(false, ex.getLocalizedMessage()),
//                HttpStatus.BAD_REQUEST);
//    }
}
