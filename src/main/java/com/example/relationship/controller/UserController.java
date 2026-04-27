package com.example.relationship.controller;

import com.example.relationship.api_model.CreateUserRequest;
import com.example.relationship.dto.UserDTO;
import com.example.relationship.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Users", description = "User management endpoints")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/users")
    @Operation(summary = "Create a user", description = "Creates a single user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created"),
            @ApiResponse(responseCode = "404", description = "User not created")
    })
    public UserDTO addUser(@RequestBody CreateUserRequest createUserRequest){
        return userService.addUser(createUserRequest);
    }

    @GetMapping("/users")
    @Operation(summary = "Gets all users", description = "Returns all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found"),
            @ApiResponse(responseCode = "404", description = "Users not found")
    })
    public List<UserDTO> findAllUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "Get user by Id", description = "Returns a single user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public UserDTO findById(@PathVariable Long userId){
        return userService.findById(userId);
    }

    @PutMapping("users/{userId}")
    @Operation(summary = "Updates a user by Id", description = "Returns an edited single user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "404", description = "User not updated")
    })
    public UserDTO updateUser(@PathVariable Long userId, @RequestBody CreateUserRequest createUserRequest){
        return userService.updateUser(userId, createUserRequest);
    }

    @PatchMapping("users/{userId}")
    @Operation(summary = "Edits one or more values of a user by Id", description = "Returns an edited single user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User edited"),
            @ApiResponse(responseCode = "404", description = "User not edited")
    })
    public UserDTO partialUpdate(@PathVariable Long userId, @RequestBody Map<String, Object> update){
        return userService.partialUpdate(userId, update);
    }

    @DeleteMapping("users/{userId}")
    @Operation(summary = "Delete user by Id", description = "Deletes a single user by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User not deleted")
    })
    public String deleteById(@PathVariable Long userId){
        return userService.deleteById(userId);
    }
}
