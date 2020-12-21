package am.neovision.admin.toolkit.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

/**
 * Created by hakob on 05/05/20.
 */
@Data
@AllArgsConstructor
public class ApiResponse {
    private Boolean success;
    private String message;
    private Integer code;

    @Nullable
    private Object body;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponse(Boolean success, String message, Integer code) {
        this.success = success;
        this.message = message;
        this.code = code;
    }
}
