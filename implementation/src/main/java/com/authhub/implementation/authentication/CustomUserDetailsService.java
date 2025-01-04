package com.authhub.implementation.authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }

    // private final UserManagementService userManagementService;

    // public CustomUserDetailsService(UserManagementService userManagementService) {
    //     this.userManagementService = userManagementService;
    // }

    // @Override
    // public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //     User user = userManagementService.getUserByUsername(username);
    //     if (user == null) {
    //         throw new UsernameNotFoundException("User not found");
    //     }
    //     return user;
    // }
}
