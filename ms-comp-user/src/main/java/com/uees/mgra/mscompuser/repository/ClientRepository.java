package com.uees.mgra.mscompuser.repository;

import com.uees.mgra.mscompuser.models.entities.Client;
import com.uees.mgra.mscompuser.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByUser(User user);
}
