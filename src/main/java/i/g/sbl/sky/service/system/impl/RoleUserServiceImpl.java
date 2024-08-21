package i.g.sbl.sky.service.system.impl;

import i.g.sbl.sky.basic.exception.NotFoundException;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.RoleUser;
import i.g.sbl.sky.repo.system.RoleUserRepo;
import i.g.sbl.sky.service.system.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleUserServiceImpl implements RoleUserService {
    @Autowired
    private RoleUserRepo roleUserRepo;


    @Override
    public Optional<RoleUser> findById(RoleUser.RoleUserId id) {
        return roleUserRepo.findById(id);
    }

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


    @Transactional
    @Override
    public void delete(RoleUser.RoleUserId id) {
        roleUserRepo.deleteById(id);
    }

    @Transactional
    @Override
    public void delete(List<RoleUser.RoleUserId> id) {
        roleUserRepo.deleteAllById(id);
    }
}
