package am.neovision.converter;

import am.neovision.dto.DatatableRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class RequestToDatatableRequestConverter implements Converter<HttpServletRequest, DatatableRequest> {
    @Override
    public DatatableRequest convert(HttpServletRequest req) {
        int draw = Integer.parseInt(req.getParameter("draw"));
        int start = Integer.parseInt(req.getParameter("start"));
        int length = Integer.parseInt(req.getParameter("length"));
        String search = req.getParameter("search[value]");
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        String orderColNumStr = req.getParameter("order[0][column]");
        if (StringUtils.isNoneBlank(orderColNumStr)) {
            Integer orderColNum = Integer.parseInt(orderColNumStr);
            String orderColName = req.getParameter("columns[" + orderColNum + "][name]");
            String orderDir = req.getParameter("order[0][dir]");
            sort = Sort.by(
                    Sort.Direction.fromString(orderDir),
                    orderColName
            );
        }
        int page = start / length; //Calculate page number

        Pageable pageable = PageRequest.of(page, length, sort);

        return DatatableRequest.builder()
                .draw(draw)
                .start(start)
                .search(search)
                .pageable(pageable)
                .build();
    }


}
