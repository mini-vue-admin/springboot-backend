package i.g.sbl.sky.basic.model;

import i.g.sbl.sky.basic.utils.Assert;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageData<T> {
    private int pageIndex;
    private int pageSize;
    private long totalCount;
    private List<T> list;

    private String sortField;
    private Sort.Direction sortOrder;


    public static <T> PageData<T> of(int pageIndex, int pageSize) {
        Assert.isTrue(pageIndex >= 1, "pageIndex must be greater than 0");
        Assert.isTrue(pageSize > 0, "pageSize must be greater than 0");
        Assert.isTrue(pageSize < 1000, "pageSize must be less than 1000");
        return new PageData<T>(pageIndex, pageSize, 0, new ArrayList<>(), null, null);
    }

    public static <T> PageData<T> of(int pageIndex, int pageSize, String sortField, Sort.Direction sortOrder) {
        Assert.isTrue(pageIndex >= 1, "pageIndex must be greater than 0");
        Assert.isTrue(pageSize > 0, "pageSize must be greater than 0");
        Assert.isTrue(pageSize < 1000, "pageSize must be less than 1000");
        return new PageData<T>(pageIndex, pageSize, 0, new ArrayList<>(), sortField, sortOrder);
    }

    public static <T> PageData<T> of(Page<T> page) {
        String sortField = null;
        Sort.Direction sortOrder = null;
        Sort sort = page.getSort();
        if (sort != null) {
            Optional<Sort.Order> first = sort.get().findFirst();
            if (first.isPresent()) {
                sortField = first.get().getProperty();
                sortOrder = first.get().getDirection();
            }
        }
        return new PageData<>(page.getPageable().getPageNumber() + 1, page.getPageable().getPageSize(), page.getTotalElements(), page.getContent(), sortField, sortOrder);
    }

    public PageRequest toPageRequest() {
        return PageRequest.of(pageIndex - 1, pageSize, Sort.by(sortOrder, sortField));
    }
}
