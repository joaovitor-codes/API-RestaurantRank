package com.dev.apirestaurantrank.mapper.impl;

import com.dev.apirestaurantrank.dto.UserRequest;
import com.dev.apirestaurantrank.dto.UserResponse;
import com.dev.apirestaurantrank.mapper.UserMapper;
import com.dev.apirestaurantrank.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapperImpl implements UserMapper {
    public UserResponse toUserResponse(UserEntity user) {
        if (user == null) {
            return null;
        }
        return new UserResponse(
                user.getId(),
                user.getName());
    }

    public UserEntity toUserEntity(UserRequest request) {
        return UserEntity.builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .build();
    }

    public Page<UserResponse> toUserResponsePage(Page<UserEntity> userPage) {
        if (userPage == null) {
            return Page.empty();
        }

        List<UserResponse> userResponses = userPage
                .getContent()
                .stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(userResponses, userPage.getPageable(), userPage.getTotalElements());
    }
}
