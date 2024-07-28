package i.g.sbl.sky.basic.jpa;

import i.g.sbl.sky.basic.jpa.filters.SimpleFilter;

import java.util.function.Function;

public interface Filter<T> {

    static <T> Filter<T> of() {
        return of(null);
    }

    static <T> Filter<T> of(T instance) {
        return new SimpleFilter<T>(instance);
    }

    <R> Filter eq(Function<T, R> property);

    <R> Filter ne(Function<T, R> property);

    <R> Filter gt(Function<T, R> property);

    <R> Filter lt(Function<T, R> property);

    <R> Filter like(Function<T, R> property);

    <R> Filter isNull(Function<T, R> property);

    <R> Filter in(Function<T, R> property);

    <R> Filter notIn(Function<T, R> property);

    <R> Filter gte(Function<T, R> property);

    <R> Filter lte(Function<T, R> property);

    Filter and(Filter<T>... filters);

    Filter or(Filter<T>... filters);

}
