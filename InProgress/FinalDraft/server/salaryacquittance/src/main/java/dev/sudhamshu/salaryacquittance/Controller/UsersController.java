package dev.sudhamshu.salaryacquittance.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.sudhamshu.salaryacquittance.Repository.UsersRepository;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersRepository usersRepository;

    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteByUsername(@PathVariable String username) {
        usersRepository.deleteByUsername(username);
        return ResponseEntity.ok("User deleted successfully");
    }

}
