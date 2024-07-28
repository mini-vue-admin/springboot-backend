package i.g.sbl.sky.basic.jpa.filters;

import i.g.sbl.sky.basic.jpa.Filter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class SimpleFilter<T> implements Filter<T> {


    private Class<T> entityClass;
    private T instance;
    private ProxyJpaEntity<T> proxyInstance;

    private boolean ignoreBlank;

    private EntityManager em;
    private CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<T> query = criteriaBuilder.createQuery(entityClass);
    Root<T> itemRoot = query.from(entityClass);
    List<Predicate> predicates = new ArrayList<>();

    public SimpleFilter(boolean ignoreBlank) {
        this.ignoreBlank = ignoreBlank;
        this.proxyInstance = new
    }

    @Override
    public Filter eq(Function<T, ?> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        Object value = property.apply((T) proxyInstance);
        if (!skip(value)) {
            predicates.add(
                    criteriaBuilder.equal(itemRoot.get(proxyInstance.getCallbackField()), value)
            );
        }
        return this;
    }


    @Override
    public Filter ne(Function<T, ?> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        return null;
    }

    @Override
    public Filter gt(Function<T, ?> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        return null;
    }

    @Override
    public Filter lt(Function<T, ?> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        return null;
    }

    @Override
    public Filter like(Function<T, ?> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        return null;
    }

    @Override
    public Filter not(Function<T, ?> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        return null;
    }

    @Override
    public Filter in(Function<T, ?> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        return null;
    }

    @Override
    public Filter notIn(Function<T, ?> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        return null;
    }

    @Override
    public Filter gte(Function<T, ?> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        return null;
    }

    @Override
    public Filter lte(Function<T, ?> property) {
        Assert.notNull(instance, "Entity instance must not be null");
        return null;
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
            if (value instanceof CharSequence) {
                return !StringUtils.hasText((CharSequence) value);
            }
            if (value instanceof Collection) {
                return ((Collection<?>) value).isEmpty();
            }
            if (value.getClass().isArray()) {
                return ((Object[]) value).length == 0;
            }
        }
        return false;
    }
}
