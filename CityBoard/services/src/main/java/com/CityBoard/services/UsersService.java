package com.CityBoard.services;

import com.CityBoard.IUsersRepository;
import com.CityBoard.UsersRepository;
import com.CityBoard.models.Users;
import com.CityBoard.models.enums.Roles;
import com.CityBoard.postgresql.dto.UserDTO;
import com.CityBoard.postgresql.repository.UsersJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
//public class UsersService extends AbstractService<UserDTO, UsersJPARepository> {
public class UsersService {
    //private final PasswordEncoder passwordEncoder;
    private final IUsersRepository usersRepository;

    //public UsersService(UsersRepository repository, PasswordEncoder passwordEncoder) {
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        //this.passwordEncoder = passwordEncoder;
    }

    //public boolean checkPassword(String encoded, String given) {
    //    return passwordEncoder.matches(encoded, given);
    //}

    public Page<Users> getAllUsersPage(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        return usersRepository.findAll(pageable);
    }

    public Users getUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    public Users getUserByPrincipal(Principal principal) {
        Users user = null;
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken)) {
            user = getUserByUsername(principal.getName());
        }
        return user;
    }

    public Users getUserById(Long userId) {
        return usersRepository.findById(userId);
    }

    //public UserDTO getUserDTOByUsername(String username) {
    //    return repository.findByUsername(username);
    //}

    //public UserDTO getUserDTOById(Long userId) {
    //    return repository.findById(userId).orElse(null);
    //}

    public boolean usernameExists(String username) {
        return usersRepository.findByUsername(username) != null;
    }

    //public String cryptPassword(String password) {
    //    if (passwordEncoder == null) {
    //        return "{noop}" + password;
    //    }
    //    return passwordEncoder.encode(password);
    //}

    public UserDTO createUser(Users user) throws Exception {
        if (usernameExists(user.getUsername())) {
            throw new Exception("User with this username already exists");
        }
        usersRepository.saveUser(user);
        return null;
    }

    //public UserDTO createUser(Users user) throws Exception {
    //    if (usernameExists(user.getUsername())) {
    //        throw new Exception("User with this username already exists");
    //    }
    //    Set<Roles> userRoles = new HashSet<>();
    //    userRoles.add(Roles.ROLE_USER);
    //    UserDTO userDTO = new UserDTO();
    //    Long savedId = userDTO.getId();
    //    //user.setPassword(cryptPassword(user.getPassword()));
    //    user.setPassword(user.getPassword());
    //    userDTO.mapEntity(user);
//
    //    userDTO.setId(savedId);
    //    userDTO.setRoles(userRoles);
    //    repository.save(userDTO);
    //    return userDTO;
    //}
//
    public boolean addRole(Long userId, Roles role) {
        return usersRepository.addRole(userId, role);
    }

    public boolean removeRole(Long userId, Roles role) {
        return usersRepository.removeRole(userId, role);
    }

    private Page<Users> mapDTOtoEntityPage(Page<UserDTO> dtoPage, Pageable pageable) {
        List<UserDTO> dtoList = dtoPage.getContent();
        long totalElements = dtoPage.getTotalElements();

        List<Users> usersList = new ArrayList<>();
        for (UserDTO dto : dtoList) {
            usersList.add(dto.mapDTOtoEntity());
        }
        return new PageImpl<>(usersList, pageable, totalElements);
    }

    //@Override
    //public void save(UserDTO entity) {
    //    repository.save(entity);
    //}
//
    //@Override
    //public void delete(UserDTO entity) {
    //    repository.delete(entity);
    //}
}
