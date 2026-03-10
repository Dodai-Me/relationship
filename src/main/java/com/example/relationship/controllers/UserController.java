package com.example.relationship.controllers;

import com.example.relationship.api_models.CreateUserRequest;
import com.example.relationship.dao.WalletDAO;
import com.example.relationship.dto.UserDTO;
import com.example.relationship.entity.User;
import com.example.relationship.dao.UserDAO;
import com.example.relationship.entity.Wallet;
import com.example.relationship.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    UserDAO userDao;
    WalletDAO walletDAO;

    @Autowired
    UserController(UserDAO userDao, WalletDAO walletDAO){
        this.userDao = userDao;
        this.walletDAO = walletDAO;
    }

    @PostMapping("/users")
    public UserDTO addUser(@RequestBody CreateUserRequest createUserRequest){
        User user = new User();
        user.setFirstName(createUserRequest.getFirstName());
        user.setLastName(createUserRequest.getLastName());

        Optional<Wallet> optionalWallet = walletDAO.findById(createUserRequest.getWalletId());

        if(optionalWallet.isPresent()){
            Wallet wallet = optionalWallet.get();
            user.setWallet(wallet);
        }

        userDao.save(user);
        return userToUserDTO(user);
    }

    @GetMapping("/users")
    public List<UserDTO> findAllUser(){
        return userDao.findAll().stream().map(this::userToUserDTO).toList();
    }

    @GetMapping("/users/{userId}")
    public UserDTO findById(@PathVariable Long userId){
        Optional<User> optionalUser = userDao.findById(userId);

        if(optionalUser.isPresent()){
            return userToUserDTO(optionalUser.get());
        }

        throw new EntityNotFoundException("Could not find User ID " + userId);
    }

    @PutMapping("users/{userId}")
    public UserDTO updateUser(@PathVariable Long userId, @RequestBody CreateUserRequest createUserRequest){
        User user = new User();
        Optional<User> optionalUser = userDao.findById(userId);

        if(optionalUser.isPresent()){
            user = optionalUser.get();
            user.setFirstName(createUserRequest.getFirstName());
            user.setLastName(createUserRequest.getLastName());
        }
        userDao.save(user);
        return userToUserDTO(user);
    }

    @DeleteMapping("users/{userId}")
    public String deleteById(@PathVariable Long userId){
         userDao.deleteById(userId);
         return "User ID " + userId + " has been deleted";
    }

    public UserDTO userToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setUuid(user.getUuid());

        if(user.getWallet() != null){
            Wallet wallet = user.getWallet();
            userDTO.setWallet(wallet);
        }

        return userDTO;
    }
}
