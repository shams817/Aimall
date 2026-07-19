package com.aimall.aimall.service;

import com.aimall.aimall.model.User;
import com.aimall.aimall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // User Registration - Create new account
    public User registerUser(String email, String fullName, String password, String phone) {
        // Check if email already exists
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Create new user
        User user = new User();
        user.setEmail(email);
        user.setFullName(fullName);
        user.setPassword(password);
        user.setPhone(phone);
        user.setAddress("");
        return userRepository.save(user);
    }

    // User Login - Authenticate user
    public User loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        
        if (!user.isPresent()) {
            throw new IllegalArgumentException("Email not found");
        }

        User foundUser = user.get();
        if (!foundUser.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid password");
        }

        return foundUser;
    }

    public User updateUser(Long id, User userDetails) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            u.setFullName(userDetails.getFullName());
            u.setEmail(userDetails.getEmail());
            u.setPassword(userDetails.getPassword());
            u.setAddress(userDetails.getAddress());
            u.setPhone(userDetails.getPhone());
            return userRepository.save(u);
        }
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
