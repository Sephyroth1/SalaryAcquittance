package dev.sudhamshu.salaryacquittance.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.sudhamshu.salaryacquittance.DTO.AuthRequest;
import dev.sudhamshu.salaryacquittance.DTO.RegisterEntity;
import dev.sudhamshu.salaryacquittance.Model.Users;
import dev.sudhamshu.salaryacquittance.Repository.UsersRepository;
import dev.sudhamshu.salaryacquittance.Security.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService,
            PasswordEncoder passwordEncoder,
            UsersRepository usersRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            UserDetails userdetails = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userdetails);
            return ResponseEntity.ok(token);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterEntity registerEntity) {
        Users user = new Users();
        user.setName(registerEntity.getName());
        user.setUsername(registerEntity.getUsername());
        user.setEmail(registerEntity.getEmail());
        user.setPassword(passwordEncoder.encode(registerEntity.getPassword()));
        user.setRole(registerEntity.getRole());
        usersRepository.save(user);
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDetails> getProfileEntity(Authentication authentication) {
        UserDetails userdetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(userdetails);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteByUsername(Authentication authentication) {
        String username = authentication.getName();
        usersRepository.deleteByUsername(username);
        return ResponseEntity.ok("User deleted successfully");
    }
}
