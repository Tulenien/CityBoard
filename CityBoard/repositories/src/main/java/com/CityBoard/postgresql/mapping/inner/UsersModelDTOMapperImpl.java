package com.CityBoard.postgresql.mapping.inner;

import com.CityBoard.dto.UserDTO;
import com.CityBoard.interfaces.dbmodels.UsersModel;
import com.CityBoard.interfaces.mapping.UsersModelDTOMapper;
import org.springframework.data.domain.Page;

import java.util.List;

public class UsersModelDTOMapperImpl implements UsersModelDTOMapper {
    @Override
    public UserDTO mapUsersModelToDTO(UsersModel usersModel) {
        return null;
    }

    @Override
    public List<UserDTO> mapUsersModelListToDTO(List<UsersModel> usersModelList) {
        return null;
    }

    @Override
    public Page<UserDTO> mapUsersModelPageToDTO(Page<UsersModel> usersModelPage) {
        return null;
    }

    @Override
    public UsersModel mapDTOtoUsersModel(UserDTO userDTO) {
        return null;
    }
}
