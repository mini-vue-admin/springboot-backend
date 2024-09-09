package i.g.sbl.sky.basic.jpa.filters;

import lombok.Data;

import java.util.List;

@Data
public class Condition<R extends Comparable<? super R>> {
    public enum Op {
        like, eq, ne, gt, lt, gte, lte, isNull, _in, notIn, and, or
    }

    private Op op;
    private String field;
    private R value;
    private List<Filter<?>> filters;

    public Condition(Op op, String field, R value) {
        this.op = op;
        this.field = field;
        this.value = value;
    }

    public Condition(Op op, String field, R value, List<Filter<?>> filters) {
        this.op = op;
        this.field = field;
        this.value = value;
        this.filters = filters;
    }
}
