package com.uees.mgra.mscompuser.service;

import com.uees.mgra.mscompuser.models.entities.Role;
import com.uees.mgra.mscompuser.models.enums.RoleName;
import com.uees.mgra.mscompuser.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Prueba de búsqueda de Rol por Nombre de Rol")
    @Test
    void testFindByRolename() {
        
        RoleName roleName = RoleName.ROLE_CLIENTE;
        Role expectedRole = new Role();
        when(roleRepository.findByRoleName(roleName)).thenReturn(Optional.of(expectedRole));

        
        Optional<Role> result = roleService.findByRolename(roleName);

       
        assertEquals(expectedRole, result.orElse(null));
        verify(roleRepository, times(1)).findByRoleName(roleName);
    }

    @DisplayName("Prueba de búsqueda de Rol por Nombre de Rol - Rol no encontrado")
    @Test
    void testFindByRolenameRoleNotFound() {
        
        RoleName roleName = RoleName.ROLE_CLIENTE;
        when(roleRepository.findByRoleName(roleName)).thenReturn(Optional.empty());

        
        Optional<Role> result = roleService.findByRolename(roleName);

       
        assertEquals(Optional.empty(), result);
        verify(roleRepository, times(1)).findByRoleName(roleName);
    }
}
