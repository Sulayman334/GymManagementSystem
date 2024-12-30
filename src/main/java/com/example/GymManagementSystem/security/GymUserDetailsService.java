package com.example.GymManagementSystem.security;

import com.example.GymManagementSystem.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class GymUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public GymUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(GymUserDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("No user with username " + username));
    }
}
