package i.g.sbl.sky.repo.system;

import com.querydsl.jpa.impl.JPAQuery;
import i.g.sbl.sky.basic.jpa.dsl.QueryDslRepository;
import i.g.sbl.sky.basic.jpa.filters.Filter;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.Menu;
import i.g.sbl.sky.entity.system.User;
import i.g.sbl.sky.entity.system.vo.MenuQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static i.g.sbl.sky.entity.system.QMenu.menu;
import static i.g.sbl.sky.entity.system.QRoleMenu.roleMenu;

public interface MenuRepo extends JpaRepository<Menu, String>, JpaSpecificationExecutor<Menu>, QueryDslRepository<Menu> {

    default List<Menu> findByFilter(Menu query) {
        return this.findAll(
                Filter.of(query)
                        .eq(Menu::getParentId)
                        .like(Menu::getMenuTitle)
                        .eq(Menu::getMenuType)
                        .like(Menu::getMenuName)
                        .like(Menu::getPath)
                        .like(Menu::getComponent)
                        .eq(Menu::getStatus)
        );
    }

    default PageData<Menu> findByFilter(Menu query, PageData<Menu> pageable) {
        Page<Menu> page = this.findAll(
                Filter.of(query)
                        .eq(Menu::getParentId)
                        .like(Menu::getMenuTitle)
                        .eq(Menu::getMenuType)
                        .like(Menu::getMenuName)
                        .like(Menu::getPath)
                        .like(Menu::getComponent)
                        .eq(Menu::getStatus),
                pageable.toPageRequest()
        );
        return PageData.of(page);
    }

    default PageData<Menu> findRoleMenus(MenuQuery query, PageData<User> pageable) {
        Page<Menu> page = findAll(
                new JPAQuery<>()
                        .select(menu)
                        .from(menu)
                        .innerJoin(roleMenu)
                        .on(menu.id.eq(roleMenu.menuId))
                        .where(roleMenu.roleId.eq(query.getRoleId()))
                        .where(StringUtils.hasText(query.getParentId()) ? menu.parentId.eq(query.getParentId()) : null),
                pageable.toPageRequest()
        );
        return PageData.of(page);
    }

    List<Menu> findByParentId(String id);

    Optional<Menu> findByMenuName(String menuName);

    default List<Menu> findByParentId(String id, boolean recursive) {
        List<Menu> children = this.findByParentId(id);
        if (recursive) {
            children.forEach(child -> {
                child.setChildren(this.findByParentId(child.getId(), true));
            });
        }
        return children;
    }
}
