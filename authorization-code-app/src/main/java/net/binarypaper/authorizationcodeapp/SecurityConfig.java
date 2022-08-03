package net.binarypaper.authorizationcodeapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            @Value("${keycloak.realm}") String realm) throws Exception {
        http
                // Configure session management to your needs.
                // I need this as a basis for a classic, server side rendered application
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
                .authorizeRequests().antMatchers("/actuator*").permitAll().and()
                // Propagate logouts via /logout to Keycloak
                .csrf().disable()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .addLogoutHandler(new KeycloakLogoutHandler())
                .and()
                // This is the point where OAuth2 login of Spring 5 gets enabled
                // .oauth2Login().userInfoEndpoint().oidcUserService(keycloakOidcUserService).and()
                .oauth2Login()
                // I don't want a page with different clients as login options
                // So I use the constant from OAuth2AuthorizationRequestRedirectFilter
                // plus the configured realm as immediate redirect to Keycloak
                .loginPage(
                        OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI + "/" + realm);
        return http.build();
    }
}