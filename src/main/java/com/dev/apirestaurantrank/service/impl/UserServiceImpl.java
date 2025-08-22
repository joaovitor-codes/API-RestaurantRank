package com.dev.apirestaurantrank.service.impl;

import com.dev.apirestaurantrank.dto.UserRequest;
import com.dev.apirestaurantrank.dto.UserResponse;
import com.dev.apirestaurantrank.dto.UserUpdate;
import com.dev.apirestaurantrank.exception.ResourceNotFoundException;
import com.dev.apirestaurantrank.mapper.UserMapper;
import com.dev.apirestaurantrank.model.UserEntity;
import com.dev.apirestaurantrank.repository.UserRepository;
import com.dev.apirestaurantrank.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Page<UserResponse> getUsers(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<UserEntity> userEntityPage = userRepository.findAll(pageable);
        return userMapper.toUserResponsePage(userEntityPage);
    }

    @Override
    public UserResponse getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        return userMapper.toUserResponse(userEntity);
    }

    @Override
    public void createUser(UserRequest userRequest) {
        UserEntity userEntity = userMapper.toUserEntity(userRequest);
        userRepository.save(userEntity);
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        userRepository.delete(userEntity);
    }

    @Override
    public void updateUser(Long id, UserUpdate userUpdate) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        userUpdate.name().ifPresent(user::setName);
        userUpdate.email().ifPresent(user::setEmail);
        userUpdate.password().ifPresent(user::setPassword);
        userRepository.save(user);
    }
}
