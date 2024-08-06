package i.g.sbl.sky.service.system.impl;

import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.RoleMenu;
import i.g.sbl.sky.repo.system.RoleMenuRepo;
import i.g.sbl.sky.service.system.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleMenuServiceImpl implements RoleMenuService {
    @Autowired
    private RoleMenuRepo roleMenuRepo;

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
}
