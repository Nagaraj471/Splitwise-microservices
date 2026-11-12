
package com.splitwise.api_gateway.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Locale;

@Component
public class JwtAuthenticationConverter implements
        Converter<Jwt, Mono<? extends AbstractAuthenticationToken>> {

    @Override
    public Mono<? extends AbstractAuthenticationToken> convert(Jwt jwt) {

        String role = jwt.getClaimAsString("role");

        List<SimpleGrantedAuthority> authorities =
                role == null
                        ? List.of()
                        : List.of(
                        new SimpleGrantedAuthority(
                                "ROLE_" + role.toUpperCase(Locale.ROOT)
                        )
                );

        return Mono.just(
                new JwtAuthenticationToken(
                        jwt,
                        authorities,
                        jwt.getSubject()
                )
        );
    }
}
