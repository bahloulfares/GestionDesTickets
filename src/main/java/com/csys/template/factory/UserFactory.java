package com.csys.template.factory;

import com.csys.template.domain.User;
import com.csys.template.dto.UserDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserFactory {
  public static UserDTO userToUserDTO(User user) {
    UserDTO userDTO=new UserDTO();
    userDTO.setUsername(user.getUsername());
    userDTO.setPassword(user.getPassword());
    userDTO.setDescription(user.getDescription());
    return userDTO;
  }

  public static User userDTOToUser(UserDTO userDTO) {
    User user=new User();
    user.setUsername(userDTO.getUsername());
    user.setPassword(userDTO.getPassword());
    user.setDescription(userDTO.getDescription());
    return user;
  }

  public static Collection<UserDTO> userToUserDTOs(Collection<User> users) {
    List<UserDTO> usersDTO=new ArrayList<>();
    users.forEach(x -> {
      usersDTO.add(userToUserDTO(x));
    } );
    return usersDTO;
  }
}

