package com.learn.jersey.filters;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

@Provider
public class ClientRequestFilterImpl implements ClientRequestFilter {

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {

        System.out.println("inside :: " +this.getClass().getName());
        MultivaluedMap<String, Object> headers = requestContext.getHeaders();
        List<Object> objects = headers.get(HttpHeaders.AUTHORIZATION);

        System.out.println(headers.toString());

        if(requestContext.getHeaders().get("Authorization") == null){
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Please provide "+HttpHeaders.AUTHORIZATION + " Header").build());
        }
    }
}
