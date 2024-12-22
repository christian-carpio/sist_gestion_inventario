package com.uees.mgra.mscompuser.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class JwtProviderTest {


    @InjectMocks
    private JwtProvider jwtProvider;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Validar token invalido")
    @Test
    void testValidateInvalidToken() {
        
        String invalidToken = "invalid-token";

        
        boolean isValid = jwtProvider.validateToken(invalidToken);

       
        assertFalse(isValid);
    }
}
