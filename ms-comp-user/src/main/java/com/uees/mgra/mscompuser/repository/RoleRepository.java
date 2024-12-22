package com.uees.mgra.mscompuser.repository;

import com.uees.mgra.mscompuser.models.entities.Role;
import com.uees.mgra.mscompuser.models.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName roleName);
}
