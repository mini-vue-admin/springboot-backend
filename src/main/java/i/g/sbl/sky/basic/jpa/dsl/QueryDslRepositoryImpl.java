package i.g.sbl.sky.basic.jpa.dsl;

import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.AbstractJPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

@Repository
public class QueryDslRepositoryImpl<T> implements QueryDslRepository<T> {

    @PersistenceContext
    private EntityManager entityManager;
//
//    @Autowired
//    private JPAQueryFactory jpaQueryFactory;

    @Override
    @SuppressWarnings("unchecked")
    public Page<T> findAll(JPQLQuery jpqlQuery, Pageable pageable) {
        Assert.notNull(jpqlQuery, "JPQLQuery must not be null!");
        Assert.notNull(pageable, "Pageable must not be null!");

        Querydsl querydsl = new Querydsl(entityManager, new PathBuilderFactory()
                .create(jpqlQuery.getType()));

        JPQLQuery<T> countQuery = ((AbstractJPAQuery) jpqlQuery).clone(entityManager);
        AbstractJPAQuery query = (AbstractJPAQuery) querydsl.applyPagination(pageable, jpqlQuery);
        return PageableExecutionUtils.getPage(
                // Clone query in order to provide entity manager instance.
                query.clone(entityManager).fetch(),
                pageable,
                countQuery::fetchCount);
    }

    @Override
    public List<T> findAll(JPQLQuery<T> jpqlQuery) {
        Assert.notNull(jpqlQuery, "JPQLQuery must not be null!");
        JPQLQuery<T> query = ((AbstractJPAQuery) jpqlQuery).clone(entityManager);
        return query.fetch();
    }

    @Override
    public <R> List<R> findAny(JPQLQuery<R> jpqlQuery) {
        Assert.notNull(jpqlQuery, "JPQLQuery must not be null!");
        JPQLQuery<R> query = ((AbstractJPAQuery) jpqlQuery).clone(entityManager);
        return query.fetch();
    }
}