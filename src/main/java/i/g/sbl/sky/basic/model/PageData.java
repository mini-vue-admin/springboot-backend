package i.g.sbl.sky.basic.model;

import lombok.Data;

import java.util.List;

@Data
public class PageData <T>{
    private int pageIndex;
    private int pageSize;
    private long totalCount;
    private List<T> list;

}
