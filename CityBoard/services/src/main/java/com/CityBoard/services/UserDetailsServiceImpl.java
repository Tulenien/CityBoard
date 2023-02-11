package com.CityBoard.services;

import com.CityBoard.tto.mapping.UsersDTOMapper;
import com.CityBoard.interfaces.UsersRepository;
import com.CityBoard.models.Users;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsersRepository usersRepository;
    private final UsersDTOMapper repositoryMapper;

    public UserDetailsServiceImpl(UsersRepository usersRepository,
                                  @Qualifier("repository") UsersDTOMapper repositoryMapper) {
        this.usersRepository = usersRepository;
        this.repositoryMapper = repositoryMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repositoryMapper.mapDTOtoUsers(usersRepository.findUserByUsername(username));
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException("Username not found");
    }
}
