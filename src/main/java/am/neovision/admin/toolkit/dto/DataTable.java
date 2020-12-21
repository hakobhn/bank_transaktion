package am.neovision.admin.toolkit.dto;

import lombok.Data;

import java.util.List;

@Data
public class DataTable<T> {

    private int draw;
    private int start;
    private long recordsActive;
    private long recordsTotal;
    private long recordsFiltered;
    private List<T> data;
}
