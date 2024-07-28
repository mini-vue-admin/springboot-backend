package i.g.sbl.sky.basic.jpa;

import i.g.sbl.sky.basic.jpa.filters.SimpleFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.function.Function;

public interface Filter<T> {

    static <T> Filter<T> of() {
        return of(true);
    }

    static <T> Filter<T> of(boolean ignoreBlank) {
        return new SimpleFilter<T>(ignoreBlank);
    }

    Filter eq(Function<T, ?> property);

    Filter ne(Function<T, ?> property);

    Filter gt(Function<T, ?> property);

    Filter lt(Function<T, ?> property);

    Filter like(Function<T, ?> property);

    Filter not(Function<T, ?> property);

    Filter in(Function<T, ?> property);

    Filter notIn(Function<T, ?> property);

    Filter gte(Function<T, ?> property);

    Filter lte(Function<T, ?> property);

    Filter and(Filter<T>... filters);

    Filter or(Filter<T>... filters);

}
