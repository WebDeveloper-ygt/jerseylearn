package com.learn.jersey.filters;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;

public class RequestFilter implements ContainerRequestFilter {
    @Context
    UriInfo uriInfo;
    @Context
    HttpHeaders httpHeaders;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String method = requestContext.getMethod();

    }
}
