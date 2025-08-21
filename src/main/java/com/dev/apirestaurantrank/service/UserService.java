package com.dev.apirestaurantrank.service;

import com.dev.apirestaurantrank.dto.UserRequest;
import com.dev.apirestaurantrank.dto.UserResponse;
import com.dev.apirestaurantrank.dto.UserUpdate;
import org.springframework.data.domain.Page;


public interface UserService {
    Page<UserResponse> getUsers(int page);
    UserResponse getUserById(Long id);
    void createUser(UserRequest userRequest);
    void deleteUser(Long id);
    void updateUser(Long id, UserUpdate userUpdate);
}
