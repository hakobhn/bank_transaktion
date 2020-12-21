package am.neovision.admin.toolkit.exceptions;

/**
 * @author hakob.hakobyan created on 9/14/2020
 */
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
