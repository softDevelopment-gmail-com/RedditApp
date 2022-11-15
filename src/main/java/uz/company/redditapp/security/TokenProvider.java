package uz.company.redditapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import uz.company.redditapp.config.ApplicationProperties;
import uz.company.redditapp.constants.AuthConstants;
import uz.company.redditapp.domain.User;
import uz.company.redditapp.domain.VerificationToken;
import uz.company.redditapp.repository.VerificationTokenRepository;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TokenProvider implements AuthConstants {

    private final long verificationTokenExpiryTimeInDays = 2;
    private final Key key;

    private final Long tokenValidityInMillis;


    private final JwtParser jwtParser;

    private final VerificationTokenRepository verificationTokenRepository;

    public TokenProvider(ApplicationProperties applicationProperties, VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
        byte[] keyBytes;
        String secret = applicationProperties.getSecurityConfig().getBase64Secret();
        keyBytes = Decoders.BASE64URL.decode(secret);
        key = Keys.hmacShaKeyFor(keyBytes);
        this.jwtParser = Jwts.parserBuilder().setSigningKey(keyBytes).build();
        this.tokenValidityInMillis = applicationProperties.getSecurityConfig().getTokenValidityInMillis();

    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        Long userId = null;
        if (authentication.getPrincipal() instanceof UserAuth) {
            UserAuth customUser = (UserAuth) authentication.getPrincipal();
            userId = customUser.getUserId();
        }
        return Jwts
                .builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .claim(USER_ID, userId)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidityInMillis))
                .compact();
    }

    public String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(LocalDateTime.now().plusDays(verificationTokenExpiryTimeInDays));
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public boolean validateToken(String jwt) {
        try {
            jwtParser.parseClaimsJws(jwt);
            return true;
        } catch (IllegalArgumentException e) {
            log.error("Invalid JWT token trace", e);
        }
        return false;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        Long userId = claims.get(USER_ID, Long.class);
        UserAuth principal = new UserAuth(claims.getSubject(), "", authorities, userId);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}
