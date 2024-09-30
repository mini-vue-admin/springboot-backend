package i.g.sbl.sky.service.system.impl;

import i.g.sbl.sky.basic.exception.NotFoundException;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.Menu;
import i.g.sbl.sky.repo.system.MenuRepo;
import i.g.sbl.sky.service.system.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepo menuRepo;


    @Override
    public Optional<Menu> findById(String id) {
        return menuRepo.findById(id);
    }

    @Override
    public List<Menu> findAll(Menu query) {
        return menuRepo.findByFilter(query);
    }

    @Override
    public PageData<Menu> findAll(Menu query, PageData<Menu> pageable) {
        return menuRepo.findByFilter(query, pageable);
    }

    @Transactional
    @Override
    public Menu create(Menu menu) {
        return menuRepo.save(menu);
    }

    @Transactional
    @Override
    public Menu update(Menu menu) {
        Menu item = menuRepo.findById(menu.getId()).orElseThrow(NotFoundException::new);
        item.copyNonNulls(menu);
        return menuRepo.save(item);
    }

    @Transactional
    @Override
    public void delete(String id) {
        menuRepo.deleteById(id);
    }

    @Transactional
    @Override
    public void delete(List<String> id) {
        menuRepo.deleteAllById(id);
    }
}
