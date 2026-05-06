package com.example.usermanagement.service;

import com.example.usermanagement.dto.UserDTO;
import com.example.usermanagement.dto.UserResponseDTO;
import java.util.List;

public interface UserService {
    UserResponseDTO createUser(UserDTO userDTO);
    UserResponseDTO updateUser(Long id, UserDTO userDTO);
    UserResponseDTO getUserById(Long id);
    List<UserResponseDTO> getAllUsers();
    List<UserResponseDTO> getUsersByDepartment(String department);
    void deleteUser(Long id);
}