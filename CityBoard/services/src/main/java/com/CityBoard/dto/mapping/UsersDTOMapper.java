package com.CityBoard.dto.mapping;

import com.CityBoard.dto.UserDTO;
import com.CityBoard.models.Users;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface UsersDTOMapper {
    UserDTO mapUsersToDTO(Users user);

    List<UserDTO> mapUsersListToDTO(List<Users> users);

    Page<UserDTO> mapUsersPageToDTO(Page<Users> users);

    Users mapDTOtoUsers(UserDTO dto);

    List<Users> mapDTOtoUsersList(List<UserDTO> dto);

    Page<Users> mapDTOtoUsersPage(Page<UserDTO> dto);
}
