package com.example.relationship.controller;

import com.example.relationship.api_model.CreateUserRequest;
import com.example.relationship.dto.UserDTO;
import com.example.relationship.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/users")
    public UserDTO addUser(@RequestBody CreateUserRequest createUserRequest){
        return userService.addUser(createUserRequest);
    }

    @GetMapping("/users")
    public List<UserDTO> findAllUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/users/{userId}")
    public UserDTO findById(@PathVariable Long userId){
        return userService.findById(userId);
    }

    @PutMapping("users/{userId}")
    public UserDTO updateUser(@PathVariable Long userId, @RequestBody CreateUserRequest createUserRequest){
        return userService.updateUser(userId, createUserRequest);
    }

    @PatchMapping("users/{userId}")
    public UserDTO partialUpdate(@PathVariable Long userId, @RequestBody Map<String, Object> update){
        return userService.partialUpdate(userId, update);
    }

    @DeleteMapping("users/{userId}")
    public String deleteById(@PathVariable Long userId){
        return userService.deleteById(userId);
    }
}
