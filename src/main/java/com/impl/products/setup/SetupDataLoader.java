package com.impl.products.setup;

import com.impl.products.dto.UserTypeEnum;
import com.impl.products.model.user.Privilege;
import com.impl.products.model.user.Role;
import com.impl.products.model.user.User;
import com.impl.products.repository.PrivilegeRepository;
import com.impl.products.repository.RoleRepository;
import com.impl.products.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${role.admin}")
    private String roleAdmin;

    @Value("${role.user}")
    private String roleUser;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        Privilege readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(
                readPrivilege, writePrivilege);
        List<Privilege> userPrivileges = Arrays.asList(readPrivilege);
        createRoleIfNotFound(roleAdmin, adminPrivileges);
        createRoleIfNotFound(roleUser, userPrivileges);

        createAdminUser();
        createViewer();
        alreadySetup = true;
    }

    @Transactional
    User createAdminUser(){
        Role adminRole = roleRepository.findByName(roleAdmin);
        User user = new User();
        user.setId(1);
        user.setFullName("Admin");
        user.setPassword(passwordEncoder.encode("test"));
        user.setUserName("admin");
        user.setUserType(UserTypeEnum.ADMIN);
        user.setRoles(Arrays.asList(adminRole));
        userRepository.save(user);
        return user;
    }
    @Transactional
    User createViewer(){
        Role userRole = roleRepository.findByName(roleUser);
        User user = new User();
        user.setId(2);
        user.setFullName("Viewer");
        user.setPassword(passwordEncoder.encode("test"));
        user.setUserName("viewer");
        user.setUserType(UserTypeEnum.USER);
        user.setRoles(Arrays.asList(userRole));
        userRepository.save(user);
        return user;
    }
    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege();
            privilege.setName(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}