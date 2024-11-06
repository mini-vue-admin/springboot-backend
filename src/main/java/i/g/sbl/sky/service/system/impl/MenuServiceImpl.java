package i.g.sbl.sky.service.system.impl;

import i.g.sbl.sky.basic.exception.BusinessException;
import i.g.sbl.sky.basic.exception.NotFoundException;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.Menu;
import i.g.sbl.sky.entity.system.vo.MenuQuery;
import i.g.sbl.sky.repo.system.MenuRepo;
import i.g.sbl.sky.repo.system.RoleMenuRepo;
import i.g.sbl.sky.service.system.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepo menuRepo;
    @Autowired
    private RoleMenuRepo roleMenuRepo;

    @Override
    public Optional<Menu> findById(String id) {
        return menuRepo.findById(id);
    }

    @Override
    public List<Menu> findAll(MenuQuery query) {
        List<Menu> menus = menuRepo.findByFilter(query);
        if (query.getChildRecursion() != null && query.getChildRecursion()) {
            for (Menu menu : menus) {
                List<Menu> children = menuRepo.findByParentId(menu.getId(), true);
                menu.setChildren(children);
            }
        }
        return menus;
    }

    @Override
    public PageData<Menu> findAll(MenuQuery query, PageData<Menu> pageable) {
        PageData<Menu> page = menuRepo.findByFilter(query, pageable);
        if (query.getChildRecursion() != null && query.getChildRecursion()) {
            for (Menu menu : page.getList()) {
                List<Menu> children = menuRepo.findByParentId(menu.getId(), true);
                menu.setChildren(children);
            }
        }
        return page;
    }

    private void validate(Menu menu) {
        menuRepo.findByMenuName(menu.getMenuName()).ifPresent(exist -> {
            if (menu.getId() == null || !Objects.equals(exist.getId(), menu.getId())) {
                throw new BusinessException("菜单名称已存在");
            }
        });
        if (Objects.equals(menu.getId(), menu.getParentId())) {
            throw new BusinessException("禁止设置自身为父节点");
        }
        List<Menu> children = menuRepo.findByParentId(menu.getId(), true);
        if (children.stream().anyMatch(it -> it.getId().equals(menu.getParentId()))) {
            throw new BusinessException("禁止设置自己的子节点为父节点");

        }
    }

    @Transactional
    @Override
    public Menu create(Menu menu) {
        validate(menu);
        return menuRepo.save(menu);
    }

    @Transactional
    @Override
    public Menu update(Menu menu) {
        validate(menu);
        Menu item = menuRepo.findById(menu.getId()).orElseThrow(NotFoundException::new);
        item.copyNonNulls(menu);
        return menuRepo.save(item);
    }

    @Transactional
    @Override
    public void delete(String id) {
        List<Menu> children = menuRepo.findByParentId(id);
        if (!children.isEmpty()) {
            throw new BusinessException("无法删除当前菜单，请先删除所有子菜单");
        }
        roleMenuRepo.deleteByMenuId(id);
        menuRepo.deleteById(id);
    }

    @Transactional
    @Override
    public void delete(List<String> id) {
        id.forEach(this::delete);
    }
}
