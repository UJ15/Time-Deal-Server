package com.uj15.timedeal.user.repository;

import com.uj15.timedeal.user.entity.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

}
