package com.CityBoard.services;

import com.CityBoard.models.Users;
import com.CityBoard.models.enums.Roles;
import com.CityBoard.postgresql.dto.UserDTO;
import com.CityBoard.postgresql.repository.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UsersService extends AbstractService<UserDTO, UsersRepository> {
    //private final PasswordEncoder passwordEncoder;

    //public UsersService(UsersRepository repository, PasswordEncoder passwordEncoder) {
    public UsersService(UsersRepository repository) {

        super(repository);
        //this.passwordEncoder = passwordEncoder;
    }

    //public boolean checkPassword(String encoded, String given) {
    //    return passwordEncoder.matches(encoded, given);
    //}

    public Page<Users> getAllUsersPage(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<UserDTO> dtoPage = repository.findAll(pageable);
        return mapDTOtoEntityPage(dtoPage, pageable);
    }

    public Users getUserByUsername(String username) {
        return repository.findByUsername(username).mapDTOtoEntity();
    }

    public Users getUserByPrincipal(Principal principal) {
        Users user = null;
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            user = getUserByUsername(principal.getName());
        }
        return user;
    }

    public Users getUserById(Long userId) {
        UserDTO user = repository.findById(userId).orElse(null);
        if (user != null) {
            return user.mapDTOtoEntity();
        }
        return null;
    }

    public UserDTO getUserDTOByUsername(String username) {
        return repository.findByUsername(username);
    }

    public UserDTO getUserDTOById(Long userId) {
        return repository.findById(userId).orElse(null);
    }

    public boolean usernameExists(String username) {
        return repository.findByUsername(username) != null;
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
        Set<Roles> userRoles = new HashSet<>();
        userRoles.add(Roles.ROLE_USER);
        UserDTO userDTO = new UserDTO();
        Long savedId = userDTO.getId();
        //user.setPassword(cryptPassword(user.getPassword()));
        user.setPassword(user.getPassword());
        userDTO.mapEntity(user);

        userDTO.setId(savedId);
        userDTO.setRoles(userRoles);
        repository.save(userDTO);
        return userDTO;
    }

    public boolean addRole(Long userId, Roles role) {
        UserDTO user = repository.findById(userId).orElse(null);
        if (user != null && !user.getRoles().contains(role)) {
            user.getRoles().add(role);
            save(user);
            return true;
        }
        return false;
    }

    public boolean removeRole(Long userId, Roles role) {
        UserDTO user = repository.findById(userId).orElse(null);
        if (user != null && user.getRoles().contains(role)) {
            user.getRoles().remove(role);
            save(user);
            return true;
        }
        return false;
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

    @Override
    public void save(UserDTO entity) {
        repository.save(entity);
    }

    @Override
    public void delete(UserDTO entity) {
        repository.delete(entity);
    }
}
