package com.example.relationship.service;

import com.example.relationship.api_model.CreateUserRequest;
import com.example.relationship.repository.UserRepository;
import com.example.relationship.repository.WalletRepository;
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
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    @Autowired
    UserService(UserRepository userRepository, WalletRepository walletRepository){
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    public UserDTO addUser(CreateUserRequest createUserRequest){
        User user = new User();
        user.setFirstName(createUserRequest.getFirstName());
        user.setLastName(createUserRequest.getLastName());

        Optional<Wallet> optionalWallet = walletRepository.findById(createUserRequest.getWalletId());

        if(optionalWallet.isPresent()){
            Wallet wallet = optionalWallet.get();
            user.setWallet(wallet);
        }

        userRepository.save(user);
        return userToUserDTO(user);
    }

    public List<UserDTO> findAllUsers(){
        return userRepository.findAll().stream().map(this::userToUserDTO).toList();
    }

    public UserDTO findById(Long id){
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isPresent()){
            return userToUserDTO(optionalUser.get());
        }

        throw new EntityNotFoundException("Could not find User ID " + id);
    }

    public UserDTO updateUser(Long id, CreateUserRequest createUserRequest){
        Optional<User> optionalUser = userRepository.findById(id);
        User user = new User();

        if(optionalUser.isPresent()){
            user = optionalUser.get();
            user.setFirstName(createUserRequest.getFirstName());
            user.setLastName(createUserRequest.getLastName());
        }
        userRepository.save(user);
        return userToUserDTO(user);
    }

    public String deleteById(Long id){
        userRepository.deleteById(id);
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
