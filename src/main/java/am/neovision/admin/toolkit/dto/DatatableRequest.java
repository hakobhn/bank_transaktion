package am.neovision.admin.toolkit.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
@Builder
public class DatatableRequest {
    private int start;
    private int draw;
    private String search;
    private Pageable pageable;
}
