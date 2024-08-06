package i.g.sbl.sky.service.system.impl;

import i.g.sbl.sky.basic.exception.NotFoundException;
import i.g.sbl.sky.basic.model.PageData;
import i.g.sbl.sky.entity.system.Role;
import i.g.sbl.sky.repo.system.RoleRepo;
import i.g.sbl.sky.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepo roleRepo;


    @Override
    public Optional<Role> findById(Long id) {
        return roleRepo.findById(id);
    }

    @Override
    public List<Role> findAll(Role query) {
        return roleRepo.findByFilter(query);
    }

    @Override
    public PageData<Role> findAll(Role query, PageData<Role> pageable) {
        return roleRepo.findByFilter(query, pageable);
    }

    @Transactional
    @Override
    public Role create(Role role) {
        return roleRepo.save(role);
    }

    @Transactional
    @Override
    public Role update(Role role) {
        Role item = roleRepo.findById(role.getId()).orElseThrow(NotFoundException::new);
        item.copyNonNulls(role);
        return roleRepo.save(item);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        roleRepo.deleteById(id);
    }

    @Transactional
    @Override
    public void delete(List<Long> id) {
        roleRepo.deleteAllById(id);
    }
}
