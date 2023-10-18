package com.letsanjoy.xsonic.service.Impl;

import com.letsanjoy.xsonic.constants.ErrorMessage;
import com.letsanjoy.xsonic.domain.User;
import com.letsanjoy.xsonic.dto.CaptchaResponse;
import com.letsanjoy.xsonic.enums.OAuth2Provider;
import com.letsanjoy.xsonic.enums.Role;
import com.letsanjoy.xsonic.exception.ApiRequestException;
import com.letsanjoy.xsonic.exception.EmailException;
import com.letsanjoy.xsonic.exception.PasswordConfirmationException;
import com.letsanjoy.xsonic.exception.PasswordException;
import com.letsanjoy.xsonic.repository.UserRepository;
import com.letsanjoy.xsonic.security.TokenProvider;
import com.letsanjoy.xsonic.security.oauth2.OAuth2UserInfo;
import com.letsanjoy.xsonic.service.AuthenticationService;
import com.letsanjoy.xsonic.service.email.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    //private final AuthenticationManager authenticationManager;
    private final RestTemplate restTemplate;
    private final MailSender mailSender;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;

    @Value("${recaptcha.secret}")
    private String secret;

    @Value("${recaptcha.url}")
    private String captchaUrl;

    @Override
    public Map<String, Object> login(String email, String password) {
        try {
            //authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ApiRequestException(ErrorMessage.EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND));
            String userRole = user.getRoles().iterator().next().name();
            String token = tokenProvider.generate(email, userRole);
            Map<String, Object> response = new HashMap<>();
            response.put("user", user);
            response.put("token", token);
            return response;
        } catch (AuthenticationException e) {
            throw new ApiRequestException(ErrorMessage.INCORRECT_PASSWORD, HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public String registerUser(User user, String captcha, String password2) {
        String url = String.format(captchaUrl, secret, captcha);
        restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponse.class);

        if (user.getPassword() != null && !user.getPassword().equals(password2)) {
            throw new PasswordException(ErrorMessage.PASSWORDS_DO_NOT_MATCH);
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailException(ErrorMessage.EMAIL_IN_USE);
        }
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setProvider(OAuth2Provider.LOCAL);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        sendEmail(user, "Activation code", "registration-template", "registrationUrl", "/activate/" + user.getActivationCode());
        return "User successfully registered.";
    }

    @Override
    @Transactional
    public User registerOauth2User(String provider, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setFirstName(oAuth2UserInfo.getFirstName());
        user.setLastName(oAuth2UserInfo.getLastName());
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setProvider(OAuth2Provider.valueOf(provider.toUpperCase()));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateOauth2User(User user, String provider, OAuth2UserInfo oAuth2UserInfo) {
        user.setFirstName(oAuth2UserInfo.getFirstName());
        user.setLastName(oAuth2UserInfo.getLastName());
        user.setProvider(OAuth2Provider.valueOf(provider.toUpperCase()));
        return userRepository.save(user);
    }

    @Override
    public String getEmailByPasswordResetCode(String code) {
        return userRepository.getEmailByPasswordResetCode(code)
                .orElseThrow(() -> new ApiRequestException(ErrorMessage.INVALID_PASSWORD_CODE, HttpStatus.BAD_REQUEST));
    }

    @Override
    @Transactional
    public String sendPasswordResetCode(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException(ErrorMessage.EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND));
        user.setPasswordResetCode(UUID.randomUUID().toString());
        userRepository.save(user);

        sendEmail(user, "Password reset", "password-reset-template", "resetUrl", "/reset/" + user.getPasswordResetCode());
        return "Reset password code is send to your E-mail";
    }

    @Override
    @Transactional
    public String passwordReset(String email, String password, String password2) {
        if (StringUtils.isEmpty(password2)) {
            throw new PasswordConfirmationException(ErrorMessage.EMPTY_PASSWORD_CONFIRMATION);
        }
        if (password != null && !password.equals(password2)) {
            throw new PasswordException(ErrorMessage.PASSWORDS_DO_NOT_MATCH);
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException(ErrorMessage.EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND));
        user.setPassword(passwordEncoder.encode(password));
        user.setPasswordResetCode(null);
        userRepository.save(user);
        return "Password successfully changed!";
    }

    @Override
    @Transactional
    public String activateUser(String code) {
        User user = userRepository.findByActivationCode(code)
                .orElseThrow(() -> new ApiRequestException(ErrorMessage.ACTIVATION_CODE_NOT_FOUND, HttpStatus.NOT_FOUND));
        user.setActivationCode(null);
        user.setActive(true);
        userRepository.save(user);
        return "User successfully activated.";
    }

    private void sendEmail(User user, String subject, String template, String urlAttribute, String urlPath) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("firstName", user.getFirstName());
        attributes.put(urlAttribute, allowedOrigins + urlPath);
        mailSender.sendMessageHtml(user.getEmail(), subject, template, attributes);
    }
}
