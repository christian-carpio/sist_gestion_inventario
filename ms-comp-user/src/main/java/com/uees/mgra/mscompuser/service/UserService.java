package com.uees.mgra.mscompuser.service;

import com.uees.mgra.mscompuser.handler.BadRequestException;
import com.uees.mgra.mscompuser.handler.NotFoundException;
import com.uees.mgra.mscompuser.jwt.JwtProvider;
import com.uees.mgra.mscompuser.models.dto.*;
import com.uees.mgra.mscompuser.models.entities.Client;
import com.uees.mgra.mscompuser.models.entities.Image;
import com.uees.mgra.mscompuser.models.entities.Role;
import com.uees.mgra.mscompuser.models.entities.User;
import com.uees.mgra.mscompuser.models.enums.RoleName;
import com.uees.mgra.mscompuser.repository.ClientRepository;
import com.uees.mgra.mscompuser.repository.ImageRepository;
import com.uees.mgra.mscompuser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final ClientRepository clientRepository;
    private final ImageRepository imageRepository;
    private final CloudinaryService cloudinaryService;
    @Value("${cloudinary.imgBaseUrl}")
    private String urlImagenBase;

    public void save(NewUser newUser) throws BadRequestException {
        if(userRepository.existsByEmail(newUser.email())){
            throw new BadRequestException("Email ya existe!");
        }
        if(userRepository.existsByUsername(newUser.username())){
            throw  new BadRequestException("Username ya existe");
        }
        User user = mapper(newUser);
        User userSaved = userRepository.save(user);

        Client client = new Client();
        client.setPuntosLealtad(0L);
        client.setUser(userSaved);
        client.setUrlImage(urlImagenBase);
        clientRepository.save(client);
    }

    private User mapper(NewUser newUser) {
        User user = new User();
        user.setFullName(newUser.name());
        user.setUsername(newUser.username());
        user.setPassword(passwordEncoder.encode(newUser.password()));
        user.setEmail(newUser.email());
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByRolename(RoleName.ROLE_CLIENTE).get());
        if (newUser.roles().contains("ROLE_REGISTER_PRODUCT"))
            roles.add(roleService.findByRolename(RoleName.ROLE_REGISTER_PRODUCT).get());
        user.setRoles(roles);
        return user;
    }

    public JwtDto login(Login login){
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.username(), login.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.generateToken(authentication);
    }

    public void updateLealtad(Long idClient) throws BadRequestException {
        Client cliente = this.clientRepository.findById(idClient).orElseThrow(() -> new BadRequestException("No existe cliente con id"));
        cliente.setPuntosLealtad(cliente.getPuntosLealtad()+1L);
        clientRepository.save(cliente);
    }
    @Transactional(readOnly = true)
    public ClientDTO infoUser(String name) throws NotFoundException {
        User user = userRepository.findByUsernameOrByEmail(name).orElseThrow(() -> new NotFoundException("No existe user"));
        Client client = this.clientRepository.findByUser(user).orElseThrow(() -> new NotFoundException("No existe cliente"));
        Optional<Client> byUser = this.clientRepository.findByUser(user);
        Long puntosLealtad = 0L;
        if(byUser.isPresent()){
            puntosLealtad = byUser.get().getPuntosLealtad();
        }
        return new ClientDTO(user.getFullName(), user.getUsername(), user.getEmail(), puntosLealtad, client.getUrlImage());
    }

    @Transactional
    public UpdateClientResponse updateInfo(String username, UpdateClientRequest request) throws BadRequestException, IOException {
        User user = this.userRepository.findByUsernameOrByEmail(username).orElseThrow(() -> new BadRequestException("No existe user"));
        Client client = this.clientRepository.findByUser(user).orElseThrow(() -> new BadRequestException("No existe cliente"));
        String url = "";
        if(StringUtils.hasText(request.image())){
            Map result = this.cloudinaryService.upload(request);
            Image imagen =
                    new Image((String)result.get("original_filename"),
                            (String)result.get("url"),
                            (String)result.get("public_id"),
                            user.getUsername());
            imageRepository.save(imagen);
            url = imagen.getImagenUrl();
            client.setUrlImage(imagen.getImagenUrl());
        }
        if(StringUtils.hasText(request.name())){
            user.setFullName(request.name());
        }
        if(StringUtils.hasText(request.password())){
            user.setPassword(passwordEncoder.encode(request.password()));
        }
        clientRepository.save(client);
        userRepository.save(user);
        return new UpdateClientResponse(url);
    }

}

