package i.g.sbl.sky.service.system.impl;

import i.g.sbl.sky.basic.exception.NotFoundException;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.RoleMenu;
import i.g.sbl.sky.repo.system.RoleMenuRepo;
import i.g.sbl.sky.service.system.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleMenuServiceImpl implements RoleMenuService {
    @Autowired
    private RoleMenuRepo roleMenuRepo;


    @Override
    public Optional<RoleMenu> findById(RoleMenu.RoleMenuId id) {
        return roleMenuRepo.findById(id);
    }

    @Override
    public List<RoleMenu> findAll(RoleMenu query) {
        return roleMenuRepo.findByFilter(query);
    }

    @Override
    public PageData<RoleMenu> findAll(RoleMenu query, PageData<RoleMenu> pageable) {
        return roleMenuRepo.findByFilter(query, pageable);
    }

    @Transactional
    @Override
    public RoleMenu create(RoleMenu roleMenu) {
        return roleMenuRepo.save(roleMenu);
    }


    @Transactional
    @Override
    public void delete(RoleMenu.RoleMenuId id) {
        roleMenuRepo.deleteById(id);
    }

    @Transactional
    @Override
    public void delete(List<RoleMenu.RoleMenuId> id) {
        roleMenuRepo.deleteAllById(id);
    }
}
