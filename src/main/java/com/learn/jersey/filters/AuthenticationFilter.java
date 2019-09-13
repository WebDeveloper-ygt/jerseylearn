package com.learn.jersey.filters;

import java.io.IOException;
import java.security.Principal;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import com.learn.jersey.annotations.Authenticate;
import com.learn.jersey.exceptions.TokenException;

import com.learn.jersey.utils.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Provider
@Authenticate
public class AuthenticationFilter implements ContainerRequestFilter {

	@Context
	UriInfo uriInfo;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		String headerString = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		String substring = headerString.substring("Bearer ".length());
		
			try {
				Jws<Claims> parseClaimsJws = Jwts.parser().setSigningKey(Constants.SECRET.getBytes("UTF-8")).parseClaimsJws(substring);
				String claim = parseClaimsJws.getBody().get("name").toString();
				if(claim != null){
					final SecurityContext securityContext = requestContext.getSecurityContext();
					requestContext.setSecurityContext(new SecurityContext() {
						@Override
						public Principal getUserPrincipal() {
							return () -> claim;
						}

						@Override
						public boolean isUserInRole(String role) {
							return role.equalsIgnoreCase("admin");
						}

						@Override
						public boolean isSecure() {
							return uriInfo.getAbsolutePath().toString().startsWith("https");
						}

						@Override
						public String getAuthenticationScheme() {
							return "Token-Based-Auth-Scheme";
						}
					});
				}
				System.out.println(claim);
			} catch (Exception exception) {  //SignatureException | ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException
				TokenException tokenException = new TokenException();
				tokenException.setDesc(exception.getMessage());
				
				if(exception instanceof ExpiredJwtException){	
					tokenException.setMess("Token is expired");
					requestContext.abortWith(Response.status(Status.UNAUTHORIZED).entity(tokenException).build());
				}else{
					tokenException.setMess("Token is invalid");
					requestContext.abortWith(Response.status(Status.UNAUTHORIZED).entity(tokenException).build());
				}
			}
	}

}
