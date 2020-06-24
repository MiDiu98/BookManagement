package com.ungmydieu.bookmanagement.configurations;

import com.ungmydieu.bookmanagement.models.dao.Role;
import com.ungmydieu.bookmanagement.models.dao.User;
import com.ungmydieu.bookmanagement.repositories.RoleRepository;
import com.ungmydieu.bookmanagement.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@Configuration
//@Profile({"!test"})
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Value("${jwt-key}")
    private String signingKey;

    private void addRoleIfMissing(String name) {
        if (roleRepository.findByName(name) == null) {
            roleRepository.save(new Role(name));
        }
    }

    private void addUserIfMissing(String email, String password, String firstname, String lastname, boolean enabled, String... roles) {
        if (userRepository.findByEmail(email) == null) {
            User user = new User(email, new BCryptPasswordEncoder().encode(password), enabled);
            user.setLastName(lastname);
            user.setFirstName(firstname);
            user.setRoles(new HashSet<>());

            for (String role : roles) {
                user.getRoles().add(roleRepository.findByName(role));
            }

            userRepository.save(user);
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        addRoleIfMissing("ROLE_ADMIN");
        addRoleIfMissing("ROLE_USER");

        addUserIfMissing("admin@email.com", "1234", "admin", "admin", true, "ROLE_ADMIN", "ROLE_USER");
        addUserIfMissing("user1@email.com", "123", "user1", "user1", true, "ROLE_USER");
        addUserIfMissing("user2@email.com", "456", "user2", "user2", true, "ROLE_USER");

        if (signingKey == null || signingKey.length() == 0) {
            String jws = Jwts.builder()
                    .setSubject("BookManagement")
                    .signWith(SignatureAlgorithm.HS256, "BookManagementApi").compact();
            System.out.println("Use this jwt key:");
            System.out.println("jwt-key=" + jws);
        }
    }
}
