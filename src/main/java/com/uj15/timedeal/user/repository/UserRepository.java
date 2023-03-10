package com.uj15.timedeal.user.repository;

import com.uj15.timedeal.user.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("select u from User u where u.username = :username")
    Optional<User> findByUsername(String username);
}
