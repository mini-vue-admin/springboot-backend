package i.g.sbl.sky.basic.jpa.filters;

import i.g.sbl.sky.basic.jpa.Filter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class SimpleFilter<T> implements Filter<T> {


    private Class<T> entityClass;
    private T instance;
    private ProxyJpaEntity<T> proxyInstance;

    private boolean ignoreBlank = true;

    private EntityManager em;
    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery<T> query;
    private Root<T> root;
    private List<Predicate> predicates = new ArrayList<>();

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
            this.entityClass = (Class<T>) instance.getClass();
        }
        this.proxyInstance = ProxyJpaEntity.getProxyJpaEntity(this.entityClass, this.instance);
        this.criteriaBuilder = em.getCriteriaBuilder();
        this.query = criteriaBuilder.createQuery(entityClass);
        this.root = query.from(entityClass);
    }

    @Override
    public <R> Filter eq(Function<T, R> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        R value = property.apply((T) proxyInstance);
        if (!skip(value)) {
            predicates.add(
                    criteriaBuilder.equal(root.get(proxyInstance.getCallbackField()), value)
            );
        }
        return this;
    }


    @Override
    public <R> Filter ne(Function<T, R> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        R value = property.apply((T) proxyInstance);
        if (!skip(value)) {
            predicates.add(
                    criteriaBuilder.notEqual(root.get(proxyInstance.getCallbackField()), value)
            );
        }
        return this;
    }

    @Override
    public <R> Filter gt(Function<T, R> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        R value = property.apply((T) proxyInstance);
        if (!skip(value)) {
            predicates.add(
                    criteriaBuilder.greaterThan(root.get(proxyInstance.getCallbackField()), value)
            );
        }
        return this;
    }

    @Override
    public <R> Filter lt(Function<T, R> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        R value = property.apply((T) proxyInstance);
        if (!skip(value)) {
            predicates.add(
                    criteriaBuilder.lessThan(root.get(proxyInstance.getCallbackField()), value)
            );
        }
        return this;
    }

    @Override
    public <R> Filter gte(Function<T, R> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        R value = property.apply((T) proxyInstance);
        if (!skip(value)) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(root.get(proxyInstance.getCallbackField()), value)
            );
        }
        return this;
    }

    @Override
    public <R> Filter lte(Function<T, R> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        R value = property.apply((T) proxyInstance);
        if (!skip(value)) {
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(root.get(proxyInstance.getCallbackField()), value)
            );
        }
        return this;
    }

    @Override
    public <R> Filter like(Function<T, R> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        R value = property.apply((T) proxyInstance);
        if (!skip(value)) {
            predicates.add(
                    criteriaBuilder.like(root.get(proxyInstance.getCallbackField()), "%" + value + "%")
            );
        }
        return this;
    }

    @Override
    public <R> Filter isNull(Function<T, R> property) {
        R value = property.apply((T) proxyInstance);
        if (!skip(value)) {
            predicates.add(
                    criteriaBuilder.isNull(root.get(proxyInstance.getCallbackField()))
            );
        }
        return this;
    }

    @Override
    public <R> Filter in(Function<T, R> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        R value = property.apply((T) proxyInstance);
        if (!skip(value)) {
            predicates.add(
                    criteriaBuilder.like(root.get(proxyInstance.getCallbackField()), "%" + value + "%")
            );
        }
        return this;
    }

    @Override
    public <R> Filter notIn(Function<T, R> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        R value = property.apply((T) proxyInstance);
        if (!skip(value)) {

        }
        return this;
    }


    @Override
    public Filter and(Filter<T>... filters) {
        return null;
    }

    @Override
    public Filter or(Filter<T>... filters) {
        return null;
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
}
