package am.neovision.exceptions;

/**
 * @author hakob.hakobyan created on 9/28/2020
 */
public class RemoteServiceException extends CustomException {
    public RemoteServiceException(String message) {
        super(message);
    }

    public RemoteServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
