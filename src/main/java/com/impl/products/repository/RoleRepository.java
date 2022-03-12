package com.impl.products.repository;

import com.impl.products.model.user.Role;
import com.impl.products.model.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String role_admin);
}
