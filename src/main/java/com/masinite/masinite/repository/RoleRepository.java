package com.masinite.masinite.repository;

import com.masinite.masinite.model.Role;
import com.masinite.masinite.model.RoleName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}