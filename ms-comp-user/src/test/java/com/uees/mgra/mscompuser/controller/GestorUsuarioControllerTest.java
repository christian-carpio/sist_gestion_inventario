package com.uees.mgra.mscompuser.controller;

import com.uees.mgra.mscompuser.handler.BadRequestException;
import com.uees.mgra.mscompuser.handler.NotFoundException;
import com.uees.mgra.mscompuser.models.dto.ClientDTO;
import com.uees.mgra.mscompuser.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GestorUsuarioControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private GestorUsuarioController usuarioController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Prueba de actualización de puntos de lealtad exitosa")
    @Test
    void testUpdateLealtadSuccess() throws BadRequestException {

        ResponseEntity<?> response = usuarioController.save(1L);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).updateLealtad(1L);
    }

    @DisplayName("Prueba de obtener información de usuario exitosa")
    @Test
    void testGetInfoUserSuccess() throws NotFoundException {
        
        when(userService.infoUser("username")).thenReturn(new ClientDTO("Nombre", "username", "email", 10L, ""));

        
        ResponseEntity<?> response = usuarioController.infoUser("username");

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof ClientDTO);
        ClientDTO clientDTO = (ClientDTO) response.getBody();
        assertEquals("Nombre", clientDTO.name());
        assertEquals("username", clientDTO.username());
        assertEquals("email", clientDTO.email());
        assertEquals(10L, clientDTO.puntoLealtad());
    }



}