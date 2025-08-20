package com.dev.apirestaurantrank.service.impl;

import com.dev.apirestaurantrank.dto.UserRequest;
import com.dev.apirestaurantrank.dto.UserResponse;
import com.dev.apirestaurantrank.dto.UserUpdate;
import com.dev.apirestaurantrank.exception.ResourceNotFoundException;
import com.dev.apirestaurantrank.model.UserEntity;
import com.dev.apirestaurantrank.repository.UserRepository;
import com.dev.apirestaurantrank.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<UserResponse> mapToUserResponsePage(Page<UserEntity> userEntityPage, Pageable pageable) {
        List<UserResponse> userResponses = userEntityPage.getContent().stream()

                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName()
                ))
                .collect(Collectors.toList());

        return new PageImpl<>(userResponses, pageable, userEntityPage.getTotalElements());
    }

    public Page<UserResponse> getUsers(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<UserEntity> userEntityPage = userRepository.findAll(pageable);
        return mapToUserResponsePage(userEntityPage, pageable);
    }

    public UserResponse getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        return new UserResponse(userEntity.getId(), userEntity.getName());
    }
    public void createUser(UserRequest userRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userRequest.name());
        userEntity.setEmail(userRequest.email());
        userEntity.setPassword(userRequest.password());
        userRepository.save(userEntity);
    }

    public void deleteUser(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        userRepository.delete(userEntity);
    }

    public void updateUser(Long id, UserUpdate userUpdate) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        userUpdate.name().ifPresent(user::setName);
        userUpdate.email().ifPresent(user::setEmail);
        userUpdate.password().ifPresent(user::setPassword);
        userRepository.save(user);
    }
}
