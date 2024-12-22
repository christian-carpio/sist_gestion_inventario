package com.uees.mgra.mscompuser.controller;

import com.uees.mgra.mscompuser.handler.BadRequestException;
import com.uees.mgra.mscompuser.handler.NotFoundException;
import com.uees.mgra.mscompuser.models.dto.UpdateClientRequest;
import com.uees.mgra.mscompuser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GestorUsuarioController {
    private final UserService userService;

    @PostMapping("/lealtad/{id}")
    public ResponseEntity<?> save(@PathVariable Long id) throws BadRequestException {
        userService.updateLealtad(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @GetMapping("/getInfoUser/{name}")
    public ResponseEntity<?> infoUser(@PathVariable String name) throws NotFoundException {
        return ResponseEntity.ok(userService.infoUser(name));
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateImage(@PathVariable("username") String username, @RequestBody UpdateClientRequest user) throws BadRequestException, IOException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateInfo(username, user));
    }
}
