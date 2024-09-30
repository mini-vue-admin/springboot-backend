package i.g.sbl.sky.basic.jpa.dsl;

import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryDslRepository<T> {

    List<T> findAll(JPQLQuery<T> jpqlQuery);

    Page<T> findAll(JPQLQuery<T> jpqlQuery, Pageable pageable);

    <R> List<R> findAny(JPQLQuery<R> jpqlQuery);

}
