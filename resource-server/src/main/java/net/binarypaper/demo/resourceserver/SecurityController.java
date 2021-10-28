package net.binarypaper.demo.resourceserver;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("security")
@PreAuthorize("isAuthenticated()")
@SecurityRequirement(name = "security_auth")
@Tag(name = "Security Controller", description = "API to retrieve security information")
public class SecurityController {

    @GetMapping("jwt")
    @Operation(
        summary = "Get JWT token",
        description = "Get the JWT token of the currently logged in user"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "OK"
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content = @Content
        )
    })
    public Map<String, Object> getJWT(@AuthenticationPrincipal Jwt jwt) {
        return jwt.getClaims();
    }

    @GetMapping("username")
    @Operation(
        summary = "Get username",
        description = "Get the username of the currently logged in user"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "OK"
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content = @Content
        )
    })
    public String getUsername(Authentication authentication) {
        return authentication.getName();
    }

    @Operation(
        summary = "Get user roles",
        description = "Get the user roles of the currently logged in user"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "OK"
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content = @Content
        )
    })
    @GetMapping("roles")
    public List<String> getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::toString).collect(Collectors.toList());
    }

}
