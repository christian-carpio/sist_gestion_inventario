package com.uees.mgra.mscompuser.service;

import com.uees.mgra.mscompuser.models.entities.Role;
import com.uees.mgra.mscompuser.models.enums.RoleName;
import com.uees.mgra.mscompuser.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Optional<Role> findByRolename(RoleName roleName){
        return roleRepository.findByRoleName(roleName);
    }
}
