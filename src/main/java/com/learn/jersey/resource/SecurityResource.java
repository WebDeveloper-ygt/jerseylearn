package com.learn.jersey.resource;

import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.learn.jersey.executor.ThreadExecutor;
import com.learn.jersey.model.JwtToken;
import com.learn.jersey.model.LoginCredentials;
import com.learn.jersey.utils.Constants;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;

@Path("/secure")
public class SecurityResource {

	static ExecutorService exutorService = ThreadExecutor.getThreadFromPool();
	
	private @Context UriInfo uriInfo;
	
	private Runnable runnable;
	private JwtBuilder builder;
	private String compact;
	private Date expiryDate;
	private JwtToken token;

	@POST
	public void getJwtToken(LoginCredentials logCreds, @Suspended AsyncResponse asyncResponse) throws InvalidKeyException, UnsupportedEncodingException{
		
		runnable = () -> {
				token = new JwtToken();		
				ZonedDateTime zonedDateTime = ZonedDateTime.now();
				Date issuedDate = Date.from(zonedDateTime.toInstant());
				expiryDate = Date.from(zonedDateTime.toInstant().plusSeconds(900));
				
					try {
						builder = Jwts.builder()
												 .setId(UUID.randomUUID().toString())
												 .setExpiration(expiryDate)
												 .claim("name", logCreds.getUserid())
												 .signWith(SignatureAlgorithm.HS256, Constants.SECRET.getBytes("UTF-8"));
					} catch (InvalidKeyException | UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				
				    compact = builder.compact();
					
					token.setToken(compact);
					token.setExpiryAt(expiryDate);	
					asyncResponse.resume(token);
			
		};
			
		exutorService.execute(runnable);
	}
}
