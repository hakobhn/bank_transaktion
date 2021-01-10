package am.neovision.payload;

import lombok.Data;
import org.springframework.lang.Nullable;

/**
 * Created by hakob on 05/05/20.
 */
@Data
public class ApiResponse {
    private Boolean success;
    private String title;
    private String message;
    private Integer code;

    @Nullable
    private Object body;

    public ApiResponse(Boolean success, String title, String message, Integer code, Object body) {
        this.success = success;
        this.title = title;
        this.message = message;
        this.code = code;
        this.body = body;
    }
}
