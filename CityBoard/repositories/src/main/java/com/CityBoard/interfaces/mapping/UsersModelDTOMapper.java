package com.CityBoard.interfaces.mapping;

import com.CityBoard.dto.UserDTO;
import com.CityBoard.interfaces.dbmodels.UsersModel;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface UsersModelDTOMapper {
    UserDTO mapUsersModelToDTO(UsersModel usersModel);

    List<UserDTO> mapUsersModelListToDTO(List<UsersModel> usersModelList);

    Page<UserDTO> mapUsersModelPageToDTO(Page<UsersModel> usersModelPage);

    UsersModel mapDTOtoUsersModel(UserDTO userDTO);
}
