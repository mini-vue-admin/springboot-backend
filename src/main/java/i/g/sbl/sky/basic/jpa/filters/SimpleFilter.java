package i.g.sbl.sky.basic.jpa.filters;

import i.g.sbl.sky.basic.jpa.Filter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class SimpleFilter<T> implements Filter<T> {

    private final List<Condition> conditions;

    private Class<T> entityClass;
    private T instance;
    private final ProxyJpaEntity<T> proxyInstance;

    private final boolean ignoreBlank = true;

    public SimpleFilter() {
        this(null);
    }

    public SimpleFilter(T instance) {
        if (instance == null) {
            Type type = this.getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType parameterizedType) {
                if (parameterizedType.getActualTypeArguments() != null && parameterizedType.getActualTypeArguments().length > 0) {
                    this.entityClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
                }
            }
        } else {
            this.instance = instance;
            this.entityClass = (Class<T>) instance.getClass();
        }
        this.proxyInstance = ProxyJpaEntity.getProxyJpaEntity(this.entityClass, this.instance);
        this.conditions = new ArrayList<>();

    }

    @Override
    public <R extends Comparable<? super R>> Filter<T> eq(Function<T, R> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        R value = property.apply((T) proxyInstance);
        if (!skip(value)) {
            this.conditions.add(
                    new Condition<>(Condition.Op.eq, proxyInstance.getCallbackField(), value)
            );
        }
        return this;
    }


    @Override
    public <R extends Comparable<? super R>> Filter<T> ne(Function<T, R> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        R value = property.apply((T) proxyInstance);
        if (!skip(value)) {
            this.conditions.add(
                    new Condition<>(Condition.Op.ne, proxyInstance.getCallbackField(), value)
            );
        }
        return this;
    }

    @Override
    public <R extends Comparable<? super R>> Filter<T> gt(Function<T, R> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        R value = property.apply((T) proxyInstance);
        if (!skip(value)) {
            this.conditions.add(
                    new Condition<>(Condition.Op.gt, proxyInstance.getCallbackField(), value)
            );
        }
        return this;
    }

    @Override
    public <R extends Comparable<? super R>> Filter lt(Function<T, R> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        R value = property.apply((T) proxyInstance);
        if (!skip(value)) {
            this.conditions.add(
                    new Condition<>(Condition.Op.lt, proxyInstance.getCallbackField(), value)
            );
        }
        return this;
    }

    @Override
    public <R extends Comparable<? super R>> Filter<T> gte(Function<T, R> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        R value = property.apply((T) proxyInstance);
        if (!skip(value)) {
            this.conditions.add(
                    new Condition<>(Condition.Op.gte, proxyInstance.getCallbackField(), value)
            );
        }
        return this;
    }

    @Override
    public <R extends Comparable<? super R>> Filter<T> lte(Function<T, R> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        R value = property.apply((T) proxyInstance);
        if (!skip(value)) {
            this.conditions.add(
                    new Condition<>(Condition.Op.lte, proxyInstance.getCallbackField(), value)
            );
        }
        return this;
    }

    @Override
    public <R extends Comparable<? super R>> Filter<T> like(Function<T, R> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        R value = property.apply((T) proxyInstance);
        if (!skip(value)) {
            this.conditions.add(
                    new Condition<>(Condition.Op.like, proxyInstance.getCallbackField(), value)
            );
        }
        return this;
    }

    @Override
    public <R extends Comparable<? super R>> Filter<T> isNull(Function<T, R> property) {
        R value = property.apply((T) proxyInstance);
        if (!skip(value)) {
            this.conditions.add(
                    new Condition<>(Condition.Op.isNull, proxyInstance.getCallbackField(), value)
            );
        }
        return this;
    }

    @Override
    public <R extends Comparable<? super R>> Filter<T> in(Function<T, R> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        R value = property.apply((T) proxyInstance);
        if (!skip(value)) {
            this.conditions.add(
                    new Condition<>(Condition.Op._in, proxyInstance.getCallbackField(), value)
            );
        }
        return this;
    }

    @Override
    public <R extends Comparable<? super R>> Filter<T> notIn(Function<T, R> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        R value = property.apply((T) proxyInstance);
        if (!skip(value)) {
            this.conditions.add(
                    new Condition<>(Condition.Op.notIn, proxyInstance.getCallbackField(), value)
            );
        }
        return this;
    }


    @Override
    public Filter<T> and(Filter<?>... filters) {
        this.conditions.add(
                new Condition(Condition.Op.or, null, null, Arrays.asList(filters))
        );
        return this;
    }

    @Override
    public Filter<T> or(Filter<?>... filters) {
        this.conditions.add(
                new Condition(Condition.Op.and, null, null, Arrays.asList(filters))
        );
        return this;
    }

    @Override
    public <R extends Comparable<? super R>> Filter<T> eq(Function<T, R> property, R value) {
        property.apply((T) proxyInstance);
        if (!skip(value)) {
            this.conditions.add(
                    new Condition<>(Condition.Op.eq, proxyInstance.getCallbackField(), value)
            );
        }
        return this;
    }

    @Override
    public <R extends Comparable<? super R>> Filter<T> ne(Function<T, R> property, R value) {
        property.apply((T) proxyInstance);
        if (!skip(value)) {
            this.conditions.add(
                    new Condition<>(Condition.Op.ne, proxyInstance.getCallbackField(), value)
            );
        }
        return this;
    }

    @Override
    public <R extends Comparable<? super R>> Filter<T> gt(Function<T, R> property, R value) {
        property.apply((T) proxyInstance);
        if (!skip(value)) {
            this.conditions.add(
                    new Condition<>(Condition.Op.gt, proxyInstance.getCallbackField(), value)
            );
        }
        return this;
    }

    @Override
    public <R extends Comparable<? super R>> Filter<T> lt(Function<T, R> property, R value) {
        property.apply((T) proxyInstance);
        if (!skip(value)) {
            this.conditions.add(
                    new Condition<>(Condition.Op.lt, proxyInstance.getCallbackField(), value)
            );
        }
        return this;
    }

    @Override
    public <R extends Comparable<? super R>> Filter<T> like(Function<T, R> property, R value) {
        property.apply((T) proxyInstance);
        if (!skip(value)) {
            this.conditions.add(
                    new Condition<>(Condition.Op.like, proxyInstance.getCallbackField(), value)
            );
        }
        return this;
    }

    @Override
    public <R extends Comparable<? super R>> Filter<T> in(Function<T, R> property, R value) {
        property.apply((T) proxyInstance);
        if (!skip(value)) {
            this.conditions.add(
                    new Condition<>(Condition.Op._in, proxyInstance.getCallbackField(), value)
            );
        }
        return this;
    }

    @Override
    public <R extends Comparable<? super R>> Filter<T> notIn(Function<T, R> property, R value) {
        value = property.apply((T) proxyInstance);
        if (!skip(value)) {
            this.conditions.add(
                    new Condition<>(Condition.Op.notIn, proxyInstance.getCallbackField(), value)
            );
        }
        return this;
    }

    @Override
    public <R extends Comparable<? super R>> Filter<T> gte(Function<T, R> property, R value) {
        property.apply((T) proxyInstance);
        if (!skip(value)) {
            this.conditions.add(
                    new Condition<>(Condition.Op.gte, proxyInstance.getCallbackField(), value)
            );
        }
        return this;
    }

    @Override
    public <R extends Comparable<? super R>> Filter<T> lte(Function<T, R> property, R value) {
        property.apply((T) proxyInstance);
        if (!skip(value)) {
            this.conditions.add(
                    new Condition<>(Condition.Op.lte, proxyInstance.getCallbackField(), value)
            );
        }
        return this;
    }

    /**
     * 如果查询条件的属性值为NULL或者空字符串、空数组，则跳过该条件
     *
     * @param value
     * @return
     */
    private boolean skip(Object value) {
        if (ignoreBlank) {
            if (value == null) {
                return true;
            }
            if (value instanceof CharSequence text) {
                return !StringUtils.hasText(text);
            }
            if (value instanceof Collection collection) {
                return collection.isEmpty();
            }
            if (value.getClass().isArray()) {
                return ((Object[]) value).length == 0;
            }
        }
        return false;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        for (Condition condition : this.conditions) {
            switch (condition.getOp()) {
                case like -> {
                    predicates.add(
                            criteriaBuilder.like(root.get(condition.getField()), "%" + condition.getValue() + "%")
                    );
                }
                case eq -> {
                    predicates.add(
                            criteriaBuilder.equal(root.get(condition.getField()), condition.getValue())
                    );
                }
                case ne -> {
                    predicates.add(
                            criteriaBuilder.notEqual(root.get(condition.getField()), condition.getValue())
                    );
                }
                case gt -> {
                    predicates.add(
                            criteriaBuilder.greaterThan(root.get(condition.getField()), condition.getValue())
                    );
                }
                case lt -> {
                    predicates.add(
                            criteriaBuilder.lessThan(root.get(condition.getField()), condition.getValue())
                    );
                }
                case gte -> {
                    predicates.add(
                            criteriaBuilder.greaterThanOrEqualTo(root.get(condition.getField()), condition.getValue())
                    );
                }
                case lte -> {
                    predicates.add(
                            criteriaBuilder.lessThanOrEqualTo(root.get(condition.getField()), condition.getValue())
                    );
                }
                case isNull -> {
                    predicates.add(
                            criteriaBuilder.isNull(root.get(condition.getField()))
                    );
                }
                case _in -> {
                    predicates.add(
                            root.get(condition.getField()).in(criteriaBuilder.literal(condition.getValue()))
                    );
                }
                case notIn -> {
                    predicates.add(
                            criteriaBuilder.not(root.get(condition.getField()).in(criteriaBuilder.literal(condition.getValue())))
                    );
                }
                case or -> {
                    List<Predicate> list = new ArrayList<>();
                    List<Filter> filters = condition.getFilters();
                    for (Filter filter : filters) {
                        Predicate predicate = filter.toPredicate(root, query, criteriaBuilder);
                        list.add(predicate);
                    }
                    predicates.add(criteriaBuilder.or(list.toArray(new Predicate[list.size()])));
                }

                case and -> {
                    List<Predicate> list = new ArrayList<>();
                    List<Filter> filters = condition.getFilters();
                    for (Filter filter : filters) {
                        Predicate predicate = filter.toPredicate(root, query, criteriaBuilder);
                        list.add(predicate);
                    }
                    predicates.add(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
                }
                default ->
                        throw new IllegalArgumentException("Unknown jpa filter condition type: " + condition.getOp());
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

    }
}
