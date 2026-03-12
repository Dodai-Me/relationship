package com.example.relationship.service;

import com.example.relationship.api_model.CreateUserRequest;
import com.example.relationship.dao.UserDAO;
import com.example.relationship.dao.WalletDAO;
import com.example.relationship.dto.UserDTO;
import com.example.relationship.entity.User;
import com.example.relationship.entity.Wallet;
import com.example.relationship.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    UserDAO userDAO;
    WalletDAO walletDAO;

    @Autowired
    UserService(UserDAO userDAO, WalletDAO walletDAO){
        this.userDAO = userDAO;
        this.walletDAO = walletDAO;
    }

    public UserDTO addUser(CreateUserRequest createUserRequest){
        User user = new User();
        user.setFirstName(createUserRequest.getFirstName());
        user.setLastName(createUserRequest.getLastName());

        Optional<Wallet> optionalWallet = walletDAO.findById(createUserRequest.getWalletId());

        if(optionalWallet.isPresent()){
            Wallet wallet = optionalWallet.get();
            user.setWallet(wallet);
        }

        userDAO.save(user);
        return userToUserDTO(user);
    }

    public List<UserDTO> findAllUsers(){
        return userDAO.findAll().stream().map(this::userToUserDTO).toList();
    }

    public UserDTO findById(Long id){
        Optional<User> optionalUser = userDAO.findById(id);

        if(optionalUser.isPresent()){
            return userToUserDTO(optionalUser.get());
        }

        throw new EntityNotFoundException("Could not find User ID " + id);
    }

    public UserDTO updateUser(Long id, CreateUserRequest createUserRequest){
        Optional<User> optionalUser = userDAO.findById(id);
        User user = new User();

        if(optionalUser.isPresent()){
            user = optionalUser.get();
            user.setFirstName(createUserRequest.getFirstName());
            user.setLastName(createUserRequest.getLastName());
        }
        userDAO.save(user);
        return userToUserDTO(user);
    }

    public String deleteById(Long id){
        userDAO.deleteById(id);
        return "User ID " + id + " has been deleted";
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
