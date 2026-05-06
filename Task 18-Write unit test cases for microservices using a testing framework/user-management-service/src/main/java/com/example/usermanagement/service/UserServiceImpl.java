package com.example.usermanagement.service;

import com.example.usermanagement.dto.UserDTO;
import com.example.usermanagement.dto.UserResponseDTO;
import com.example.usermanagement.model.User;
import com.example.usermanagement.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public UserResponseDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setDepartment(userDTO.getDepartment());
        
        User savedUser = userRepository.save(user);
        
        UserResponseDTO response = new UserResponseDTO();
        response.setId(savedUser.getId());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setEmail(savedUser.getEmail());
        response.setPhoneNumber(savedUser.getPhoneNumber());
        response.setDepartment(savedUser.getDepartment());
        
        return response;
    }
    
    @Override
    public UserResponseDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return null;
        
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setDepartment(userDTO.getDepartment());
        
        User updatedUser = userRepository.save(user);
        
        UserResponseDTO response = new UserResponseDTO();
        response.setId(updatedUser.getId());
        response.setFirstName(updatedUser.getFirstName());
        response.setLastName(updatedUser.getLastName());
        response.setEmail(updatedUser.getEmail());
        response.setPhoneNumber(updatedUser.getPhoneNumber());
        response.setDepartment(updatedUser.getDepartment());
        
        return response;
    }
    
    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return null;
        
        UserResponseDTO response = new UserResponseDTO();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setDepartment(user.getDepartment());
        
        return response;
    }
    
    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDTO> responseList = new ArrayList<>();
        
        for (User user : users) {
            UserResponseDTO response = new UserResponseDTO();
            response.setId(user.getId());
            response.setFirstName(user.getFirstName());
            response.setLastName(user.getLastName());
            response.setEmail(user.getEmail());
            response.setPhoneNumber(user.getPhoneNumber());
            response.setDepartment(user.getDepartment());
            responseList.add(response);
        }
        
        return responseList;
    }
    
    @Override
    public List<UserResponseDTO> getUsersByDepartment(String department) {
        List<User> users = userRepository.findByDepartment(department);
        List<UserResponseDTO> responseList = new ArrayList<>();
        
        for (User user : users) {
            UserResponseDTO response = new UserResponseDTO();
            response.setId(user.getId());
            response.setFirstName(user.getFirstName());
            response.setLastName(user.getLastName());
            response.setEmail(user.getEmail());
            response.setPhoneNumber(user.getPhoneNumber());
            response.setDepartment(user.getDepartment());
            responseList.add(response);
        }
        
        return responseList;
    }
    
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}