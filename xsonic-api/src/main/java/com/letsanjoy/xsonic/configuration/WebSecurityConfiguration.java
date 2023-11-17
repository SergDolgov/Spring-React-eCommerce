package com.letsanjoy.xsonic.configuration;

import com.letsanjoy.xsonic.security.TokenAuthenticationFilter;
import com.letsanjoy.xsonic.security.oauth2.CustomAuthenticationSuccessHandler;
import com.letsanjoy.xsonic.security.oauth2.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    private final CustomOAuth2UserService customOauth2UserService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(
                                antMatcher("/api/v1/auth/**"),
                                antMatcher("/api/v1/auth/login"),
                                antMatcher("/api/v1/registration/**"),
                                antMatcher("/api/v1/order/**"),
                                antMatcher("/api/v1/review/**"),
                                antMatcher("/api/v1/users/cart"),
                                antMatcher("/api/v1/products/**")).permitAll()
                        .requestMatchers(
                                antMatcher("/api/v1/user/orders"),
                                antMatcher("/api/v1/user/addresses")).hasAnyAuthority(USER)
                        .requestMatchers(
                                antMatcher("/api/v1/admin/products"),
                                antMatcher("/api/v1/admin/users")).hasAnyAuthority(ADMIN)
                        .requestMatchers(
                                antMatcher("/auth/**"),
                                antMatcher("/oauth2/**")).permitAll()
                        .requestMatchers(
                                antMatcher("/"),
                                antMatcher("/error"),
                                antMatcher("/csrf"),
                                antMatcher("/img/**"),
                                antMatcher("/static/**"),
                                antMatcher("/websocket"),
                                antMatcher("/websocket/**"),
                                antMatcher("/swagger-ui.html"),
                                antMatcher("/swagger-ui/**"),
                                antMatcher("/v3/api-docs"),
                                antMatcher("/v3/api-docs/**")).permitAll()
                        .anyRequest().authenticated()
                        )
                .oauth2Login(oauth2Login -> oauth2Login
                        .userInfoEndpoint(userInfoEndpoint ->
                                userInfoEndpoint
                                        .userService(customOauth2UserService))
                        .successHandler(customAuthenticationSuccessHandler))
                .logout(l -> l.logoutSuccessUrl("/").permitAll())
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
}
