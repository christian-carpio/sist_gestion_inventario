package com.uees.mgra.mscompuser.service;

import com.uees.mgra.mscompuser.handler.BadRequestException;
import com.uees.mgra.mscompuser.handler.NotFoundException;
import com.uees.mgra.mscompuser.jwt.JwtProvider;
import com.uees.mgra.mscompuser.models.dto.ClientDTO;
import com.uees.mgra.mscompuser.models.dto.JwtDto;
import com.uees.mgra.mscompuser.models.dto.Login;
import com.uees.mgra.mscompuser.models.dto.NewUser;
import com.uees.mgra.mscompuser.models.entities.Client;
import com.uees.mgra.mscompuser.models.entities.Role;
import com.uees.mgra.mscompuser.models.entities.User;
import com.uees.mgra.mscompuser.models.enums.RoleName;
import com.uees.mgra.mscompuser.repository.ClientRepository;
import com.uees.mgra.mscompuser.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Prueba de registro de nuevo usuario")
    @Test
    void testSave() throws BadRequestException {
        
        NewUser newUser = new NewUser("name", "username", "password", "email", Collections.singleton("ROLE_CLIENTE"));
        when(userRepository.existsByEmail(newUser.email())).thenReturn(false);
        when(userRepository.existsByUsername(newUser.username())).thenReturn(false);
        when(roleService.findByRolename(RoleName.ROLE_CLIENTE)).thenReturn(Optional.of(new Role()));
        when(passwordEncoder.encode(newUser.password())).thenReturn("encoded-password");

        
        userService.save(newUser);


        verify(userRepository, times(1)).save(any(User.class));
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @DisplayName("Prueba de inicio de sesión exitoso")
    @Test
    void testLoginSuccess() {
        
        Login login = new Login("username", "password");
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtProvider.generateToken(authentication)).thenReturn(new JwtDto("test-token"));

        
        JwtDto jwtDto = userService.login(login);

       
        assertNotNull(jwtDto);
        assertEquals("test-token", jwtDto.token());
    }

    @DisplayName("Prueba de actualización de puntos de lealtad")
    @Test
    void testUpdateLealtad() throws BadRequestException {
        
        Client client = new Client();
        client.setPuntosLealtad(5L);
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));

        
        userService.updateLealtad(1L);

       
        assertEquals(6L, client.getPuntosLealtad());
        verify(clientRepository, times(1)).save(client);
    }

    @DisplayName("Prueba de información de usuario")
    @Test
    void testInfoUser() throws  NotFoundException {
        
        User user = new User();
        user.setFullName("Test User");
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        when(userRepository.findByUsernameOrByEmail(anyString())).thenReturn(Optional.of(user));
        when(clientRepository.findByUser(user)).thenReturn(Optional.empty());

        
        ClientDTO clientDTO = userService.infoUser("testuser");

       
        assertNotNull(clientDTO);
        assertEquals("Test User", clientDTO.name());
        assertEquals("testuser", clientDTO.username());
        assertEquals("test@example.com", clientDTO.email());
        assertEquals(0L, clientDTO.puntoLealtad());
    }

    @DisplayName("Prueba de registro de nuevo usuario exitoso")
    @Test
    void testSaveNewUserSuccess() throws BadRequestException {
        
        NewUser newUser = new NewUser("Test User", "testuser", "test@example.com", "password", Collections.singleton("ROLE_CLIENTE"));
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(roleService.findByRolename(RoleName.ROLE_CLIENTE)).thenReturn(Optional.of(new Role()));
        when(userRepository.save(any())).thenReturn(new User());

        
        assertDoesNotThrow(() -> userService.save(newUser));
        verify(userRepository, times(1)).save(any());
        verify(clientRepository, times(1)).save(any());
    }

    @DisplayName("Prueba de registro de nuevo usuario con email existente")
    @Test
    void testSaveNewUserWithExistingEmail() {
        
        NewUser newUser = new NewUser("Test User", "testuser", "test@example.com", "password", Collections.singleton("ROLE_CLIENTE"));
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        
        assertThrows(BadRequestException.class, () -> userService.save(newUser));
        verify(userRepository, never()).save(any());
        verify(clientRepository, never()).save(any());
    }

    @DisplayName("Prueba de registro de nuevo usuario con username existente")
    @Test
    void testSaveNewUserWithExistingUsername() {
        
        NewUser newUser = new NewUser("Test User", "testuser", "test@example.com", "password", Collections.singleton("ROLE_CLIENTE"));
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        
        assertThrows(BadRequestException.class, () -> userService.save(newUser));
        verify(userRepository, never()).save(any());
        verify(clientRepository, never()).save(any());
    }
}
