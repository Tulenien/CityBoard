package com.CityBoard.dto.mapping;

import com.CityBoard.dto.UserDTO;
import com.CityBoard.models.Users;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;

import java.util.List;

@Qualifier("controller")
public class UsersDTOMapperImpl implements UsersDTOMapper {
    @Override
    public UserDTO mapUsersToDTO(Users user) {
        return null;
    }

    @Override
    public List<UserDTO> mapUsersListToDTO(List<Users> users) {
        return null;
    }

    @Override
    public Page<UserDTO> mapUsersPageToDTO(Page<Users> users) {
        return null;
    }

    @Override
    public Users mapDTOtoUsers(UserDTO dto) {
        return null;
    }

    @Override
    public List<Users> mapDTOtoUsersList(List<UserDTO> dto) {
        return null;
    }

    @Override
    public Page<Users> mapDTOtoUsersPage(Page<UserDTO> dto) {
        return null;
    }
}
