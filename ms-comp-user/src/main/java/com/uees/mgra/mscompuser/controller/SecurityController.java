package com.uees.mgra.mscompuser.controller;


import com.uees.mgra.mscompuser.handler.BadRequestException;
import com.uees.mgra.mscompuser.models.dto.JwtDto;
import com.uees.mgra.mscompuser.models.dto.Login;
import com.uees.mgra.mscompuser.models.dto.NewUser;
import com.uees.mgra.mscompuser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SecurityController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> save(@RequestBody NewUser newUser) throws BadRequestException {
        userService.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/login")
    public ResponseEntity<JwtDto> save(@RequestBody Login login) {
        JwtDto jwtDto = userService.login(login);
        return ResponseEntity.ok(jwtDto);
    }
}
