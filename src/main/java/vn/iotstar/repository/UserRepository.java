package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.iotstar.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    @Query("""
        SELECT u FROM User u
        JOIN FETCH u.role
        WHERE lower(u.username) = lower(:login) OR lower(u.email) = lower(:login)
    """)
    Optional<User> findByUsernameOrEmailWithRole(@Param("login") String login);
}
