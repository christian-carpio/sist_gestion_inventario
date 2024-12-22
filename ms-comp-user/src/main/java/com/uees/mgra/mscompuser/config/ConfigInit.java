package com.uees.mgra.mscompuser.config;

import com.uees.mgra.mscompuser.handler.BadRequestException;
import com.uees.mgra.mscompuser.models.dto.NewUser;
import com.uees.mgra.mscompuser.models.entities.Role;
import com.uees.mgra.mscompuser.models.entities.User;
import com.uees.mgra.mscompuser.models.enums.RoleName;
import com.uees.mgra.mscompuser.repository.RoleRepository;
import com.uees.mgra.mscompuser.repository.UserRepository;
import com.uees.mgra.mscompuser.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Configuracion inicial de los usuarios para el proyecto
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class ConfigInit implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        initRole();
        initUsers();
    }


    private void initRole() {

        Optional<Role> roleRegisterProduct = roleRepository.findByRoleName(RoleName.ROLE_REGISTER_PRODUCT);
        Optional<Role> roleClient = roleRepository.findByRoleName(RoleName.ROLE_CLIENTE);
        
        if(roleRegisterProduct.isEmpty()){
            Role userAdmin = new Role();
            userAdmin.setRoleName(RoleName.ROLE_REGISTER_PRODUCT);
            roleRepository.save(userAdmin);
        }else if(roleClient.isEmpty())
        {
            Role userNormal = new Role();
            userNormal.setRoleName(RoleName.ROLE_CLIENTE);
            roleRepository.save(userNormal);
        }
        
        
    }

    private void initUsers() throws BadRequestException {
        NewUser user = new NewUser("Gabriel Guerrero", "gabrielg", "gabrielg@gmail.com","123", new HashSet<>());
        NewUser admin = new NewUser("Mayless Rivas", "maylessr", "maylessr@gmail.com","maylessr", Set.of("ROLE_REGISTER_PRODUCT"));
        Optional<User> gabrielg = userRepository.findByUsernameOrByEmail("gabrielg");
        Optional<User> maylessr = userRepository.findByUsernameOrByEmail("maylessr");
        if(maylessr.isEmpty())
        {
            userService.save(admin);    
        } else if (gabrielg.isEmpty()) {
            userService.save(user);
        }

        


    }

}