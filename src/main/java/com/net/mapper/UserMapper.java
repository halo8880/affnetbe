package com.net.mapper;

import com.net.dto.UserDTO;
import com.net.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserDTO userToUserDTO(User user);
}
