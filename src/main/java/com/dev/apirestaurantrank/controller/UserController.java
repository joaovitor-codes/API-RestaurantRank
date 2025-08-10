package com.dev.apirestaurantrank.controller;

import com.dev.apirestaurantrank.dto.UserRequest;
import com.dev.apirestaurantrank.dto.UserResponse;
import com.dev.apirestaurantrank.dto.UserUpdate;
import com.dev.apirestaurantrank.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<UserResponse>> getAllUsers(@PathVariable int page) {
        Page<UserResponse> userPage = userService.getUsers(page);
        return ResponseEntity.ok(userPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse userResponse = userService.getUserById(id);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody UserUpdate userUpdate) {
        userService.updateUser(id, userUpdate);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchUser(@PathVariable Long id, @RequestBody UserUpdate userUpdate) {
        userService.updateUser(id, userUpdate);
        return ResponseEntity.noContent().build();
    }
}
