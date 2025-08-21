package com.dev.apirestaurantrank.mapper;

import com.dev.apirestaurantrank.dto.UserRequest;
import com.dev.apirestaurantrank.dto.UserResponse;
import com.dev.apirestaurantrank.model.UserEntity;
import org.springframework.data.domain.Page;

public interface UserMapper {
    UserResponse toUserResponse(UserEntity user);
    UserEntity toUserEntity(UserRequest request);
    Page<UserResponse> toUserResponsePage(Page<UserEntity> userPage);
}
