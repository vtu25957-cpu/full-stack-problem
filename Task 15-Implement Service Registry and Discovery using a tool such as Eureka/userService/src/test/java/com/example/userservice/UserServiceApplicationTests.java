package com.example.userservice;

import com.example.userservice.controller.UserController;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void contextLoads() {
        assertThat(userController).isNotNull();
    }

    @Test
    void testCreateUser() {
        User user = new User("Test User", "test@example.com", "IT");
        
        ResponseEntity<User> response = restTemplate.postForEntity(
            "http://localhost:" + port + "/api/users",
            user,
            User.class
        );
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Test User");
        assertThat(response.getBody().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void testGetAllUsers() {
        // Create test users
        userRepository.save(new User("User1", "user1@test.com", "HR"));
        userRepository.save(new User("User2", "user2@test.com", "Finance"));
        
        ResponseEntity<User[]> response = restTemplate.getForEntity(
            "http://localhost:" + port + "/api/users",
            User[].class
        );
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isEqualTo(2);
    }

    @Test
    void testGetUserById() {
        User savedUser = userRepository.save(new User("Test User", "test@test.com", "Sales"));
        
        ResponseEntity<User> response = restTemplate.getForEntity(
            "http://localhost:" + port + "/api/users/" + savedUser.getId(),
            User.class
        );
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Test User");
    }

    @Test
    void testDeleteUser() {
        User savedUser = userRepository.save(new User("Test Delete", "delete@test.com", "IT"));
        
        restTemplate.delete("http://localhost:" + port + "/api/users/" + savedUser.getId());
        
        ResponseEntity<User> response = restTemplate.getForEntity(
            "http://localhost:" + port + "/api/users/" + savedUser.getId(),
            User.class
        );
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testServiceInfo() {
        ResponseEntity<String> response = restTemplate.getForEntity(
            "http://localhost:" + port + "/api/users/service-info",
            String.class
        );
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("User Service is running");
    }
}