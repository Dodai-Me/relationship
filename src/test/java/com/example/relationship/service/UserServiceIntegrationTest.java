package com.example.relationship.service;

import com.example.relationship.api_model.CreateUserRequest;
import com.example.relationship.dto.UserDTO;
import com.example.relationship.entity.User;
import com.example.relationship.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceIntegrationTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    private Long savedUserId;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setFirstName("Dodai");
        user.setLastName("Ukoko");
        user.setUuid(UUID.randomUUID().toString());

        User savedUser = userRepository.save(user);
        this.savedUserId = savedUser.getId();
    }

    @Test
    void updateUserTakesEffect(){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setFirstName("Daniella");
        createUserRequest.setFirstName("Emmanuella");

        UserDTO userDTO = userService.updateUser(savedUserId, createUserRequest);

        assertNotNull(userDTO);
        assertEquals(savedUserId, userDTO.getId());
        assertEquals("Daniella", userDTO.getFirstName());
        assertEquals("Emmanuella", userDTO.getLastName());

        User databaseUser = userRepository.findById(savedUserId).orElseThrow(() -> new AssertionError("This user was deleted. Could not be found in DB"));

        assertEquals("Daniella", databaseUser.getFirstName());
        assertEquals("Emmanuella", databaseUser.getLastName());
    }
}