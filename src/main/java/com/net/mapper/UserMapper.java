package com.net.mapper;

import com.net.dto.user.SignUpUserDTO;
import com.net.dto.user.UserCredentials;
import com.net.dto.user.UserDTO;
import com.net.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserDTO userToUserDTO(User user);

	SignUpUserDTO userToSignUpUserDTO(User user);

	User signUpUserDTOtoUser(SignUpUserDTO signUpUserDTO);

	User userCredentialstoUser(UserCredentials userCredentials);
}
