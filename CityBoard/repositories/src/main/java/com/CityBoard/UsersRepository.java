package com.CityBoard;

import com.CityBoard.models.Users;
import com.CityBoard.models.enums.Roles;
import com.CityBoard.postgresql.dto.UserDTO;
import com.CityBoard.postgresql.repository.UsersJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UsersRepository implements IUsersRepository {

    private final UsersJPARepository usersJPARepository;

    public UsersRepository(UsersJPARepository usersJPARepository) {
        this.usersJPARepository = usersJPARepository;
    }

    @Override
    public Page<Users> findAll(Pageable pageable) {
        Page<UserDTO> userDTOPage = usersJPARepository.findAll(pageable);
        return mapDTOtoEntityPage(userDTOPage, pageable);
    }

    @Override
    public Users findByUsername(String username) {
        return usersJPARepository.findByUsername(username).mapDTOtoEntity();
    }

    @Override
    public Users findById(Long id) {
        UserDTO userDTO = usersJPARepository.findById(id).orElse(null);
        if (userDTO != null) {
            return userDTO.mapDTOtoEntity();
        }
        return null;
    }

    @Override
    public void saveUser(Users user) {
        Set<Roles> userRoles = new HashSet<>();
        userRoles.add(Roles.ROLE_USER);
        UserDTO userDTO = new UserDTO();
        Long savedId = userDTO.getId();
        //user.setPassword(cryptPassword(user.getPassword()));
        user.setPassword(user.getPassword());
        userDTO.mapEntity(user);

        userDTO.setId(savedId);
        userDTO.setRoles(userRoles);
        usersJPARepository.save(userDTO);
    }

    @Override
    public boolean addRole(Long userId, Roles role) {
        UserDTO userDTO = usersJPARepository.findById(userId).orElse(null);
        if (userDTO != null && !userDTO.getRoles().contains(role)) {
            userDTO.getRoles().add(role);
            usersJPARepository.save(userDTO);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeRole(Long userId, Roles role) {
        UserDTO userDTO = usersJPARepository.findById(userId).orElse(null);
        if (userDTO != null && userDTO.getRoles().contains(role)) {
            userDTO.getRoles().remove(role);
            usersJPARepository.save(userDTO);
            return true;
        }
        return false;
    }

    private Page<Users> mapDTOtoEntityPage(Page<UserDTO> dtoPage,
                                           Pageable pageable) {
        List<UserDTO> dtoList = dtoPage.getContent();
        long totalElements = dtoPage.getTotalElements();

        List<Users> usersList = new ArrayList<>();
        for (UserDTO dto : dtoList) {
            usersList.add(dto.mapDTOtoEntity());
        }
        return new PageImpl<>(usersList, pageable, totalElements);
    }
}
