package com.letsanjoy.xsonic.security.oauth2;

import lombok.SneakyThrows;

import javax.naming.AuthenticationException;
import java.util.Map;

public class OAuth2UserFactory {

    @SneakyThrows
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(OAuth2Provider.GOOGLE.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(OAuth2Provider.FACEBOOK.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(OAuth2Provider.GITHUB.toString())) {
            return new GithubOAuth2UserInfo(attributes);
        } else {
            throw new AuthenticationException();
        }
    }
}
