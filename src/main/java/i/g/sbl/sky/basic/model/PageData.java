package i.g.sbl.sky.basic.model;

import i.g.sbl.sky.basic.utils.Assert;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageData <T>{
    private int pageIndex;
    private int pageSize;
    private long totalCount;
    private List<T> list;


    public static PageRequest of(int pageIndex, int pageSize) {
        Assert.isTrue(pageIndex >= 1, "pageIndex must be greater than 0");
        Assert.isTrue(pageSize > 0, "pageSize must be greater than 0");
        Assert.isTrue(pageSize < 1000, "pageSize must be less than 1000");
        return PageRequest.of(pageIndex - 1, pageSize);
    }
}
