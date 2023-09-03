package com.Spring.Quiz_Application.configuration;

import com.Spring.Quiz_Application.entity.User;
import com.Spring.Quiz_Application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Optional<User> user= Optional.ofNullable(repository.findByEmail(username));

        return user.map(CustomUserDetails::new).orElseThrow(()->new UsernameNotFoundException("This User does not Exist"));





    }
}
