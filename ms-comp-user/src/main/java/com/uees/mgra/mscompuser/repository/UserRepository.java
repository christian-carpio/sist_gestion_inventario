package com.uees.mgra.mscompuser.repository;

import com.uees.mgra.mscompuser.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.username=:username or u.email=:username")
    Optional<User> findByUsernameOrByEmail(@Param("username") String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email );

}
