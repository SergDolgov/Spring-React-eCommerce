package com.letsanjoy.xsonic.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import static com.letsanjoy.xsonic.constants.PathConstants.BEARER_KEY_SECURITY_SCHEME;

@OpenAPIDefinition(
        info = @Info(
                title = "XSonic System Api",
                description = "XSonic System", version = "1.0.0",
                contact = @Contact(
                        name = "Sergey Dolgov",
                        email = "saddolgov@gmail.com",
                        url = "https://sergeydolgov.dev"
                )
        )
)
@SecurityScheme(
        name = BEARER_KEY_SECURITY_SCHEME,
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfiguration {

}
