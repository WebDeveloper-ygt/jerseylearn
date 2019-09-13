package com.learn.jersey.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WebExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Context
    HttpHeaders httpHeaders;
    @Context 
    UriInfo uriInfo;
    CustomException customException;

    @Override
    public Response toResponse(WebApplicationException exception) {
        System.out.println("inside :: " + WebExceptionMapper.class.getName());
        customException = new CustomException();

        Response response =exception.getResponse();
        System.out.println("Response :: " + response + " ::  message :: " +exception.getMessage() + " :: "+ response.getMetadata().toString());
        if(response.getStatus() == 405){
            customException.setStatus(response.getStatus());
            customException.setMessage(exception.getMessage());
            customException.setDescription(response.getMetadata().toString() + " :: " + "Methods used are not allowed, please check headers");
            customException.setLink(uriInfo.getAbsolutePath().toString());
            return  Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(customException).type(httpHeaders.getMediaType()).build();
        }else if( response.getStatus() == 404){
            customException.setStatus(response.getStatus());
            customException.setMessage(exception.getMessage());
            customException.setDescription("Requested resource not Found");
            customException.setLink(uriInfo.getAbsolutePath().toString());
            return  Response.status(Response.Status.NOT_FOUND).entity(customException).type(httpHeaders.getMediaType()).build();
        }
        
        return  Response.serverError().entity(exception).type(httpHeaders.getMediaType()).build();
       // 
    }
}
