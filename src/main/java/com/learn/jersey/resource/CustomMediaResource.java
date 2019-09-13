package com.learn.jersey.resource;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.DatatypeConverter;

import com.learn.jersey.model.Course;
import com.learn.jersey.model.MyDate;
import com.learn.jersey.model.ZonedDateTimeModel;

@Path("/custom")
@Produces(MediaType.APPLICATION_JSON)
public class CustomMediaResource {



	@GET
	@Path("date/{input}")
	@Produces("application/date")
	public Response getCustomDatetime(@PathParam("input") String input)
	{
		System.out.println("application/date :: " + input);
		return Response.status(Status.OK).entity(ZonedDateTime.now()).build();
	}

	@GET
	@Path("/time/{input}")
	@Produces(MediaType.APPLICATION_JSON)
	public MyDate getCustomDatetime2(@PathParam("input") MyDate input)
	{
		System.out.println("paramConvertor :: " + input);
		return input;
	}

	@GET
	@Path("{input}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getCustomDatetime1(@PathParam("input") String input)
	{
		System.out.println("text/plain :: " + input);
		return Response.status(Status.OK).entity(ZonedDateTime.now().toString()).build();
	}
	
	@GET
	@Path("/{input}")
	@Produces(MediaType.APPLICATION_JSON)
	public ZonedDateTimeModel getCustomDatetime3(@PathParam("input") ZonedDateTimeModel input)
	{
		System.out.println("paramConvertor :: " + input);
		return input;
	}

	@GET
	@Path("/cache")
	@Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
	public Response testCaching(@Context Request request) throws NoSuchAlgorithmException {
/*
		String hello = "Hello world";
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(10000);
		EntityTag entityTag = new EntityTag(Integer.toHexString(hello.hashCode()));
		System.out.println(entityTag);
		Response.ResponseBuilder responseBuilder = request.evaluatePreconditions(entityTag);
		if(responseBuilder != null){
			responseBuilder.cacheControl(cacheControl).tag(entityTag).build();
		}
		System.out.println(responseBuilder);
		return Response.ok().entity(hello).cacheControl(cacheControl).tag(entityTag).build();*/

		/*Response.ResponseBuilder builder = request.evaluatePreconditions(lastModified);
		System.out.println(builder);
		if (builder != null) {
			return builder.build();
		}
		return Response.ok(data).lastModified(lastModified).build();*/
		GenericEntity<List<Course>> courses = CourseResource.getCourses();
		List<Course> course = courses.getEntity();
		System.out.println(course.getClass());
		MessageDigest digest = MessageDigest.getInstance("MD5");
		byte[] hash = digest.digest(courses.getEntity().getClass().toString().getBytes(StandardCharsets.UTF_8));
		String hex = DatatypeConverter.printHexBinary(hash);
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(1000);
		EntityTag etag = new EntityTag(hex);
		System.out.println(etag);
		Response.ResponseBuilder builder = request.evaluatePreconditions(etag);
		System.out.println(builder);
		if (builder != null) {
			return builder.entity(courses).cacheControl(cacheControl).build();
		}

		return Response.ok(courses).tag(etag).cacheControl(cacheControl).build();

	}
}
