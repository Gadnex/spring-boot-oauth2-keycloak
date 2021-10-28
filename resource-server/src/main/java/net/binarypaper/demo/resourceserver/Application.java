package net.binarypaper.demo.resourceserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "${info.app.name}", 
		description = "${info.app.description}", 
		version = "${info.app.version}", 
		contact = @Contact(
			name = "${info.app.developer.name}", 
			email = "${info.app.developer.email}"
		), 
		license = @License(
			name = "${info.app.license.name}", 
			url = "${info.app.license.url}"
		)
	)
)
@SecurityScheme(
	name = "security_auth", 
	type = SecuritySchemeType.OAUTH2, 
	flows = @OAuthFlows(
		authorizationCode = @OAuthFlow(
			authorizationUrl = "${spring.security.oauth2.resourceserver.jwt.issuer-uri}/protocol/openid-connect/auth",
			tokenUrl = "${spring.security.oauth2.resourceserver.jwt.issuer-uri}/protocol/openid-connect/token"
		)
	)
)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}