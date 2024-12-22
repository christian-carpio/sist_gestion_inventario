package com.uees.mgra.mscompuser.service;

import com.uees.mgra.mscompuser.models.entities.User;
import com.uees.mgra.mscompuser.models.entities.UserPrincipal;
import com.uees.mgra.mscompuser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthencationServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrByEmail(username).orElseThrow(()-> new UsernameNotFoundException("No se ha encontrado usuario"));
        return UserPrincipal.build(user);
    }
}
