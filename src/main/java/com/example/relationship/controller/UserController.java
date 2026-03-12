package com.example.relationship.controller;

import com.example.relationship.api_model.CreateUserRequest;
import com.example.relationship.dto.UserDTO;
import com.example.relationship.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    UserService userService;

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

    @DeleteMapping("users/{userId}")
    public String deleteById(@PathVariable Long userId){
        return userService.deleteById(userId);
    }
}
