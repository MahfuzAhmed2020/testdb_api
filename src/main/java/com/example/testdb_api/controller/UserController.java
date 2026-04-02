package com.example.testdb_api.controller;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;

import com.example.testdb_api.entity.User;
import com.example.testdb_api.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/limit")
public List<User> getLimitedUsers(@RequestParam int size) {
    Page<User> page = userRepository.findAll(PageRequest.of(0, size));
    return page.getContent();
}

    

@GetMapping("/range")
public List<User> getUsersInRange(@RequestParam int start, @RequestParam int end) {
    if (start < 0 || end <= start) {
        throw new RuntimeException("Invalid range");
    }
    List<User> allUsers = userRepository.findAll();
    // Handle case where end is greater than total users
    end = Math.min(end, allUsers.size());
    return allUsers.subList(start, end);  // from start (inclusive) to end (exclusive)
}

@GetMapping("/select")
public List<User> getUsersByIds(@RequestParam List<Long> ids) {
    return userRepository.findAllById(ids);
}


    // GET product by id
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
         return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
    // GET just the name of a user by id
    @GetMapping("/{id}/name")
    public String getUserNameById(@PathVariable Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return user.getName();
    }
        // GET just the name of a user by id
    @GetMapping("/{id}/email")
    public String getUserEmailById(@PathVariable Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return user.getEmail();
    }

        // GET just the name of a user by id
    @GetMapping("/{id}/address")
    public String getUserAddressById(@PathVariable Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return user.getAddress();
    }

// Update an existing user (full update)
@PutMapping("/{id}")
public User updateUser(@PathVariable Long id, @RequestBody User user) {
    User existingUser = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    
    // Update fields
    existingUser.setName(user.getName());
    existingUser.setEmail(user.getEmail());
    existingUser.setAddress(user.getAddress());
    
    return userRepository.save(existingUser);
}

// // Alternative approach using Optional for cleaner code
// @PatchMapping("/{id}")
// public User patchUser(@PathVariable Long id, @RequestBody User user) {
//     User existingUser = userRepository.findById(id)
//         .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    
//     // Only update fields if they are not null
//     if (user.getName() != null) {
//         existingUser.setName(user.getName());
//     }
    
//     if (user.getEmail() != null) {
//         existingUser.setEmail(user.getEmail());
//     }
    
//     if (user.getAddress() != null) {
//         existingUser.setAddress(user.getAddress());
//     }
        
//     return  userRepository.save(existingUser);
// }




// PATCH mapping with change tracking - using User object with detailed response
@PatchMapping("/{id}")
public Map<String, Object> patchUser(@PathVariable Long id, @RequestBody User user) {
    // Find the existing user
    User existingUser = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    
    // Track changes
    Map<String, Object> changedFields = new HashMap<>();
    
    // Only update fields if they are not null and track what changed
    if (user.getName() != null) {
        String oldValue = existingUser.getName();
        String newValue = user.getName();
        existingUser.setName(newValue);
        
        Map<String, String> change = new HashMap<>();
        change.put("old", oldValue);
        change.put("new", newValue);
        changedFields.put("name", change);
    }
    
    if (user.getEmail() != null) {
        String oldValue = existingUser.getEmail();
        String newValue = user.getEmail();
        existingUser.setEmail(newValue);
        
        Map<String, String> change = new HashMap<>();
        change.put("old", oldValue);
        change.put("new", newValue);
        changedFields.put("email", change);
    }
    
    if (user.getAddress() != null) {
        String oldValue = existingUser.getAddress();
        String newValue = user.getAddress();
        existingUser.setAddress(newValue);
        
        Map<String, String> change = new HashMap<>();
        change.put("old", oldValue);
        change.put("new", newValue);
        changedFields.put("address", change);
    }
    
    // Save the updated user
    User updatedUser = userRepository.save(existingUser);
    
    // Prepare response with both changes and full user data
    Map<String, Object> response = new HashMap<>();
    response.put("message", "User updated successfully");
    response.put("changedFields", changedFields);
    response.put("user", updatedUser);
     
    
    return response;
}


@DeleteMapping("/{id}")
public Map<String, Object> deleteUser(@PathVariable Long id) {
    // Find the user (will throw exception if not found)
    User existingUser = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    
    // Delete the user
    userRepository.deleteById(id);
        // Prepare response (similar structure to your PATCH response)
    Map<String, Object> response = new HashMap<>();
    response.put("message", "User deleted successfully");
    response.put("deletedUser", existingUser);
    // Return the deleted user (similar to PATCH returning the updated user)
    return response;
}

    // Create a new user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}