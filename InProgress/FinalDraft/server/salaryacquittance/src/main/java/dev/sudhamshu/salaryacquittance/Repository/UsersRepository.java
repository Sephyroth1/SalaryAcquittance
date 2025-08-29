package dev.sudhamshu.salaryacquittance.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import dev.sudhamshu.salaryacquittance.Model.Users;

public interface UsersRepository extends JpaRepository<Users, UUID> {
    Optional<Users> findByUsername(String username);

    @Transactional // <--- ADD THIS ANNOTATION
    @Modifying
    @Query("DELETE FROM Users u WHERE u.username = ?1")
    void deleteByUsername(String username);
}
