package i.g.sbl.sky.service.system.impl;

import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.RoleUser;
import i.g.sbl.sky.repo.system.RoleUserRepo;
import i.g.sbl.sky.service.system.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleUserServiceImpl implements RoleUserService {
    @Autowired
    private RoleUserRepo roleUserRepo;

    @Override
    public List<RoleUser> findAll(RoleUser query) {
        return roleUserRepo.findByFilter(query);
    }

    @Override
    public PageData<RoleUser> findAll(RoleUser query, PageData<RoleUser> pageable) {
        return roleUserRepo.findByFilter(query, pageable);
    }

    @Transactional
    @Override
    public RoleUser create(RoleUser roleUser) {
        return roleUserRepo.save(roleUser);
    }
}
