package com.learn.jersey.bodyreadwrite;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.ZonedDateTime;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

@Provider
@Produces("application/date")
public class DateBodyWriter implements MessageBodyWriter<ZonedDateTime> {

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		System.out.println("inside : " + this.getClass().getName());
		return ZonedDateTime.class.isAssignableFrom(type);
	}

	@Override
	public void writeTo(
					ZonedDateTime t,
					Class<?> type, 
					Type genericType,
					Annotation[] annotations, 
					MediaType mediaType,
					MultivaluedMap<String, Object> httpHeaders, 
					OutputStream entityStream
			) throws IOException, WebApplicationException {
		
		ZonedDateTime now = ZonedDateTime.now();
		
	    entityStream.write(now.toString().getBytes());
	}

	

}
