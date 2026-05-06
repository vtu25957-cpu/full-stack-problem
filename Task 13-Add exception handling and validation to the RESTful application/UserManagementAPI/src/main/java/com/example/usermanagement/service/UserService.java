package com.example.usermanagement.service;

import com.example.usermanagement.exception.UserNotFoundException;
import com.example.usermanagement.exception.ValidationException;
import com.example.usermanagement.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    
    private Map<Long, User> userDatabase = new HashMap<>();
    private long currentId = 1;
    
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        for (Long key : userDatabase.keySet()) {
            users.add(userDatabase.get(key));
        }
        return users;
    }
    
    public User getUserById(Long id) {
        if (userDatabase.containsKey(id)) {
            return userDatabase.get(id);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }
    
    public User createUser(User user) {
        if (user.getEmail() == null) {
            throw new ValidationException("Email required");
        }
        
        // Check duplicate email
        for (Long key : userDatabase.keySet()) {
            User existing = userDatabase.get(key);
            if (existing.getEmail().equals(user.getEmail())) {
                throw new ValidationException("Email exists");
            }
        }
        
        user.setId(currentId);
        userDatabase.put(currentId, user);
        currentId = currentId + 1;
        
        return user;
    }
    
    public User updateUser(Long id, User userDetails) {
        User existing = getUserById(id);
        
        existing.setName(userDetails.getName());
        existing.setEmail(userDetails.getEmail());
        existing.setAge(userDetails.getAge());
        existing.setPhone(userDetails.getPhone());
        existing.setAddress(userDetails.getAddress());
        
        userDatabase.put(id, existing);
        
        return existing;
    }
    
    public void deleteUser(Long id) {
        if (userDatabase.containsKey(id)) {
            userDatabase.remove(id);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }
    
    public User findByEmail(String email) {
        for (Long key : userDatabase.keySet()) {
            User user = userDatabase.get(key);
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        throw new UserNotFoundException("Email not found");
    }
}