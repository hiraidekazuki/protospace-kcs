package in.tech_camp.protospace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.tech_camp.protospace.custom_user.CustomUserDetails;
import in.tech_camp.protospace.entity.UserEntity;
import in.tech_camp.protospace.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
     public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByEmail(email);
    if (user == null) {
        throw new UsernameNotFoundException("User not found with email: " + email);
    }
    return new CustomUserDetails(user);
}
}