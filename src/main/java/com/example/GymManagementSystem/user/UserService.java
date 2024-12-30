package com.example.GymManagementSystem.user;

import com.example.GymManagementSystem.exceptions.NullDetails;
import com.example.GymManagementSystem.exceptions.ObjectAlreadyExist;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User addUser(User user) {
        if (user == null) {
            throw new NullDetails("User details cannot be null.");
        }

        if (!(Objects.equals(user.getRoles(), "ADMIN") || Objects.equals(user.getRoles(), "USER"))) {
            throw new IllegalArgumentException("Role must be either ADMIN or USER");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ObjectAlreadyExist("Email is already in use.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    // Delete a user by ID
    public void deleteUserById(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist.");
        }
        userRepository.deleteById(userId);
    }

    // Update an existing user
    public User updateUser(Long userId, User updatedUser) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " does not exist."));

        // Update the fields
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setRoles(updatedUser.getRoles());

        return userRepository.save(existingUser);
    }

    // Get a user by ID
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found."));
    }

    // Get all users
    public List<User> getAllUsers() {
        if (userRepository.count() == 0) {
            throw new IllegalArgumentException("No available user in the database");
        }
        return userRepository.findAll();
    }
}
