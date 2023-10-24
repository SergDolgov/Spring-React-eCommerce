package com.letsanjoy.xsonic.security;

import com.letsanjoy.xsonic.domain.User;
import com.letsanjoy.xsonic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.letsanjoy.xsonic.constants.ErrorMessage.EMAIL_NOT_ACTIVATED;
import static com.letsanjoy.xsonic.constants.ErrorMessage.USER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
        if (!user.isActive() || user.getActivationCode() != null) {
            throw new LockedException(EMAIL_NOT_ACTIVATED);
        }
        return UserPrincipal.create(user);
    }
}
