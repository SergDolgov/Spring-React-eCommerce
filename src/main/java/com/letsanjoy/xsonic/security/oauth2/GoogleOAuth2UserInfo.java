package com.letsanjoy.xsonic.security.oauth2;

import com.letsanjoy.xsonic.security.UserPrincipal;
import com.letsanjoy.xsonic.security.WebSecurityConfig;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

        public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
            super(attributes);
        }

        @Override
        public String getId() {
            return (String) attributes.get("sub");
        }

        @Override
        public String getFirstName() {
            return (String) attributes.get("given_name");
        }

        @Override
        public String getLastName() {
            return (String) attributes.get("family_name");
        }

        @Override
        public String getEmail() {
            return (String) attributes.get("email");
        }

}
