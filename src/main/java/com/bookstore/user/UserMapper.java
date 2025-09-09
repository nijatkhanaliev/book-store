package com.bookstore.user;

import com.bookstore.auth.RegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface UserMapper {

    @Mapping(target = "userRole", expression = "java(com.bookstore.user.UserRole.CUSTOMER)")
    @Mapping(target = "password", ignore = true)
    User toUser(RegisterRequest request);

}
