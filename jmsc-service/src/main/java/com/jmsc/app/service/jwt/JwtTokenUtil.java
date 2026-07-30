/**
 * 
 */
package com.jmsc.app.service.jwt;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * The JwtTokenUtil is responsible for performing JWT operations like creation and validation. 
 * It makes use of the io.jsonwebtoken.Jwts for achieving this.
 * 
 * @author chandan singh
 *
 */
@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;
	
	/**
	 * The secret key is combined with the header and the payload to create a unique hash. 
	 * We are only able to verify this hash if you have the secret key.
	 */
	@Value("${jwt.secret}")
	private String secretKey;
	
	//validate token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	
	//retrieve username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	
	//retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	
//	for retrieveing any information from token we will need the secret key
	
	private Claims getAllClaimsFromToken(String token) {
		
//		Commented below code because it is was only for java 8
//		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		
		
//		Added below code to work in JAVA 21
	    Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

	    return Jwts.parserBuilder()
	            .setSigningKey(key)
	            .build()
	            .parseClaimsJws(token)
	            .getBody();
	}
	
	

	
	//check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
}
