package i.g.sbl.sky.basic.jpa;

import i.g.sbl.sky.basic.jpa.filters.SimpleFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.function.Function;

public interface Filter<T> extends Specification<T> {

    static <T> Filter<T> of() {
        return new SimpleFilter<>();
    }

    static <T> Filter<T> of(T instance) {
        return new SimpleFilter<T>(instance);
    }

    <R extends Comparable<? super R>> Filter<T> eq(Function<T, R> property);

    <R extends Comparable<? super R>> Filter<T> ne(Function<T, R> property);

    <R extends Comparable<? super R>> Filter<T> gt(Function<T, R> property);

    <R extends Comparable<? super R>> Filter<T> lt(Function<T, R> property);

    <R extends Comparable<? super R>> Filter<T> like(Function<T, R> property);

    <R extends Comparable<? super R>> Filter<T> isNull(Function<T, R> property);

    <R extends Comparable<? super R>> Filter<T> in(Function<T, R> property);

    <R extends Comparable<? super R>> Filter<T> notIn(Function<T, R> property);

    <R extends Comparable<? super R>> Filter<T> gte(Function<T, R> property);

    <R extends Comparable<? super R>> Filter<T> lte(Function<T, R> property);

    Filter<T> and(Filter<?>... filters);

    Filter<T> or(Filter<?>... filters);

}
