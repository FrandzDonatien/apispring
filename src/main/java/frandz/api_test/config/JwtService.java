package frandz.api_test.config;

import frandz.api_test.constant.SecurityConstant;
import frandz.api_test.model.Jwt;
import frandz.api_test.model.User;
import frandz.api_test.repository.JwtRepository;
import frandz.api_test.responses.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class JwtService {

    private final JwtRepository jwtRepo;
    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
    public String generateRefreshToken(UserDetails userDetails) {
        return generateRefreshToken(new HashMap<>(), userDetails);
    }

    public String generateRefreshToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setIssuer(SecurityConstant.GET_ARRAYS_LLC)
                .setAudience(SecurityConstant.GET_ARRAYS_ADMINISTRATION)
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setIssuer(SecurityConstant.GET_ARRAYS_LLC)
                .setAudience(SecurityConstant.GET_ARRAYS_ADMINISTRATION)
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Jwt tokenByValue(String value){
        return this.jwtRepo.findByValeur(value).orElseThrow( () -> new RuntimeException("utilisateur introuvable"));
    }

    public JwtResponse getJwtToken(Authentication authentication) {
        if (!(authentication.getPrincipal() instanceof UserDetails)) {
            throw new BadCredentialsException("Phone or password is incorrect");
        }
        String refreshToken;
        if (authentication.getCredentials() instanceof Jwt) {
            if (this.isTokenValid(((Jwt) authentication.getCredentials()).getValeur(), (UserDetails) authentication.getPrincipal()))
                refreshToken = ((Jwt) authentication.getCredentials()).getValeur();
            else {
                refreshToken= this.generateRefreshToken((UserDetails) authentication.getPrincipal());
            }
        } else {
            refreshToken= generateRefreshToken((UserDetails) authentication.getPrincipal());
        }
        return JwtResponse.builder()
                .accessToken(generateToken((UserDetails) authentication.getPrincipal()))
                .refreshToken(refreshToken)
                .build();

    }

    public void deconnexion(User user){
        Jwt jwt = this.jwtRepo.findUserValidToken(user.getEmail(), false, false).orElseThrow(() -> new RuntimeException(" invalid Token"));
        jwt.setDesactive(true);
        jwt.setExpire(true);
        this.jwtRepo.save(jwt);
    }
}
