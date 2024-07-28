package i.g.sbl.sky.basic.jpa;

import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.Config;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface JpaFilter<T> extends JpaSpecificationExecutor<T> {

   default List<T> findByFilter(Filter<T> filter) {
       return findAll(filter.toSpecification());
   }

    default List<T> findByFilter(T query) {
        return null;
    }

    default PageData<T> findByFilter(T query, Pageable pageable) {
        return null;
    }

    public static void main(String[] args) {
        JpaFilter<Config> f;
        Config config =new Config();
        List<Config> list = f.findByFilter(
                Filter.of()
                        .eq(config::getId)
                        .like(config.getConfigKey())
                        .and(
                                Filter.of().eq(Config::getConfigKey), Filter.of().ne(Config::getRemark)
                        ).from(config)

        );
    }
}
