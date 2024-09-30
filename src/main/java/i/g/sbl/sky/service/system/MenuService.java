package i.g.sbl.sky.service.system;

import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuService {

    Optional<Menu> findById(String id);

    List<Menu> findAll(Menu query);

    PageData<Menu> findAll(Menu query, PageData<Menu> pageable);

    Menu create(Menu menu);

    Menu update(Menu menu);

    void delete(String id);

    void delete(List<String> id);

}
