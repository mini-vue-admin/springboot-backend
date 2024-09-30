package i.g.sbl.sky.basic.jpa.filters;

import org.springframework.data.jpa.domain.Specification;

import java.util.function.Function;

/**
 * 定义了一个泛型接口Filter，用于构建和应用JPA查询的过滤条件。
 * 该接口继承自Spring Data JPA的Specification接口，提供了一组方法来定义各种查询条件。
 *
 * @param <T> 指定过滤条件应用的实体类型。
 */
public interface Filter<T> extends Specification<T> {

    /**
     * 创建一个新的空Filter实例。
     *
     * @return 返回一个空的Filter实例。
     */
    static <T> Filter<T> of() {
        return new SimpleFilter<>();
    }

    /**
     * 创建一个新的Filter实例，并使用给定的实例初始化。
     *
     * @param clazz 过滤对象的类型
     * @return 返回一个使用给定实例初始化的Filter实例。
     */
    static <T> Filter<T> of(Class<T> clazz) {
        return new SimpleFilter<>(clazz);
    }

    /**
     * 创建一个新的Filter实例，并使用给定的实例初始化。
     *
     * @param instance 用于初始化Filter的实例。
     * @return 返回一个使用给定实例初始化的Filter实例。
     */
    static <T> Filter<T> of(T instance) {
        return new SimpleFilter<T>(instance);
    }

    /**
     * 构建一个等于条件的过滤。
     *
     * @param property 用于提取属性值的函数。
     * @return 返回一个新的Filter实例，应用了等于条件。
     */
    <R extends Comparable<? super R>> Filter<T> eq(Function<T, R> property);

    /**
     * 构建一个不等于条件的过滤。
     *
     * @param property 用于提取属性值的函数。
     * @return 返回一个新的Filter实例，应用了不等于条件。
     */
    <R extends Comparable<? super R>> Filter<T> ne(Function<T, R> property);

    /**
     * 构建一个大于条件的过滤。
     *
     * @param property 用于提取属性值的函数。
     * @return 返回一个新的Filter实例，应用了大于条件。
     */
    <R extends Comparable<? super R>> Filter<T> gt(Function<T, R> property);

    /**
     * 构建一个小于条件的过滤。
     *
     * @param property 用于提取属性值的函数。
     * @return 返回一个新的Filter实例，应用了小于条件。
     */
    <R extends Comparable<? super R>> Filter<T> lt(Function<T, R> property);

    /**
     * 构建一个模糊匹配条件的过滤。
     *
     * @param property 用于提取属性值的函数。
     * @return 返回一个新的Filter实例，应用了模糊匹配条件。
     */
    <R extends Comparable<? super R>> Filter<T> like(Function<T, R> property);

    /**
     * 构建一个属性值为null的条件的过滤。
     *
     * @param property 用于提取属性值的函数。
     * @return 返回一个新的Filter实例，应用了属性值为null的条件。
     */
    <R extends Comparable<? super R>> Filter<T> isNull(Function<T, R> property);

    /**
     * 构建一个属性值在指定集合中的过滤。
     *
     * @param property 用于提取属性值的函数。
     * @return 返回一个新的Filter实例，应用了属性值在指定集合中条件。
     */
    <R extends Comparable<? super R>> Filter<T> in(Function<T, R> property);

    /**
     * 构建一个属性值不在指定集合中的过滤。
     *
     * @param property 用于提取属性值的函数。
     * @return 返回一个新的Filter实例，应用了属性值不在指定集合中条件。
     */
    <R extends Comparable<? super R>> Filter<T> notIn(Function<T, R> property);

    /**
     * 构建一个大于等于条件的过滤。
     *
     * @param property 用于提取属性值的函数。
     * @return 返回一个新的Filter实例，应用了大于等于条件。
     */
    <R extends Comparable<? super R>> Filter<T> gte(Function<T, R> property);

    /**
     * 构建一个小于等于条件的过滤。
     *
     * @param property 用于提取属性值的函数。
     * @return 返回一个新的Filter实例，应用了小于等于条件。
     */
    <R extends Comparable<? super R>> Filter<T> lte(Function<T, R> property);

    /**
     * 将当前过滤与另一个或多个过滤组合，使用逻辑与操作。
     *
     * @param filters 要与当前过滤组合的过滤条件数组。
     * @return 返回一个新的Filter实例，应用了逻辑与条件。
     */
    Filter<T> and(Filter<?>... filters);

    /**
     * 将当前过滤与另一个或多个过滤组合，使用逻辑或操作。
     *
     * @param filters 要与当前过滤组合的过滤条件数组。
     * @return 返回一个新的Filter实例，应用了逻辑或条件。
     */
    Filter<T> or(Filter<?>... filters);


    /**
     * 构建一个属性值等于特定值的条件的过滤。
     *
     * @param property 用于提取属性值的函数。
     * @param value    要比较的值。
     * @return 返回一个新的Filter实例，应用了属性值等于特定值的条件。
     */
    <R extends Comparable<? super R>> Filter<T> eq(Function<T, R> property, R value);

    /**
     * 构建一个属性值不等于特定值的条件的过滤。
     *
     * @param property 用于提取属性值的函数。
     * @param value    要比较的值。
     * @return 返回一个新的Filter实例，应用了属性值不等于特定值的条件。
     */
    <R extends Comparable<? super R>> Filter<T> ne(Function<T, R> property, R value);

    /**
     * 构建一个属性值大于特定值的条件的过滤。
     *
     * @param property 用于提取属性值的函数。
     * @param value    要比较的值。
     * @return 返回一个新的Filter实例，应用了属性值大于特定值的条件。
     */
    <R extends Comparable<? super R>> Filter<T> gt(Function<T, R> property, R value);

    /**
     * 构建一个属性值小于特定值的条件的过滤。
     *
     * @param property 用于提取属性值的函数。
     * @param value    要比较的值。
     * @return 返回一个新的Filter实例，应用了属性值小于特定值的条件。
     */
    <R extends Comparable<? super R>> Filter<T> lt(Function<T, R> property, R value);

    /**
     * 构建一个属性值与特定值进行模糊匹配的条件的过滤。
     *
     * @param property 用于提取属性值的函数。
     * @param value    要进行模糊匹配的值。
     * @return 返回一个新的Filter实例，应用了属性值模糊匹配特定值的条件。
     */
    <R extends Comparable<? super R>> Filter<T> like(Function<T, R> property, R value);

    /**
     * 构建一个属性值包含在特定集合中的过滤。
     *
     * @param property 用于提取属性值的函数。
     * @param value    要检查是否包含的值。
     * @return 返回一个新的Filter实例，应用了属性值包含在特定集合中的条件。
     */
    <R extends Comparable<? super R>> Filter<T> in(Function<T, R> property, R value);

    /**
     * 构建一个属性值不包含在特定集合中的过滤。
     *
     * @param property 用于提取属性值的函数。
     * @param value    要检查是否不包含的值。
     * @return 返回一个新的Filter实例，应用了属性值不包含在特定集合中的条件。
     */
    <R extends Comparable<? super R>> Filter<T> notIn(Function<T, R> property, R value);

    /**
     * 构建一个属性值大于等于特定值的条件的过滤。
     *
     * @param property 用于提取属性值的函数。
     * @param value    要比较的值。
     * @return 返回一个新的Filter实例，应用了属性值大于等于特定值的条件。
     */
    <R extends Comparable<? super R>> Filter<T> gte(Function<T, R> property, R value);

    /**
     * 构建一个属性值小于等于特定值的条件的过滤。
     *
     * @param property 用于提取属性值的函数。
     * @param value    要比较的值。
     * @return 返回一个新的Filter实例，应用了属性值小于等于特定值的条件。
     */
    <R extends Comparable<? super R>> Filter<T> lte(Function<T, R> property, R value);

}
