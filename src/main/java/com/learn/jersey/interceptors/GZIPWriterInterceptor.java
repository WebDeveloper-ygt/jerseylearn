package com.learn.jersey.interceptors;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

@Provider
public class GZIPWriterInterceptor implements WriterInterceptor {
    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
        System.out.println("inside :: " + this.getClass().getName());

        MultivaluedMap<String,Object> headers = context.getHeaders();
        headers.add("Content-Encoding", "gzip");

        OutputStream outputStream = context.getOutputStream();
       context.setOutputStream(new GZIPOutputStream(outputStream));
        context.proceed();
    }
}
