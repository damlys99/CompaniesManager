package com.example.uniprogramming.security;

import com.example.uniprogramming.models.User;
import com.example.uniprogramming.models.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByUserName(userName);

        user.orElseThrow(() -> new UsernameNotFoundException("Not Found: "+ userName));

        return user.map(MyUserDetails::new).get();

    }
}
