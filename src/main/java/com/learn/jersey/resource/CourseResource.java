package com.learn.jersey.resource;

import com.learn.jersey.annotations.Authenticate;
import com.learn.jersey.exceptions.CustomException;
import com.learn.jersey.executor.ThreadExecutor;
import com.learn.jersey.model.Course;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.server.ChunkedOutput;

import javax.annotation.ManagedBean;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.CompletionCallback;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@ManagedBean
@Path("/courses")
public class CourseResource {

    private static int numberOfSuccessResponses = 0;
    private static int numberOfFailures = 0;
    private static Throwable lastException = null;

    static ExecutorService executorService = ThreadExecutor.getThreadFromPool();
    private String result;

    public static GenericEntity<List<Course>> getCourses() {
        System.out.println("Called");
        List<Course> courses = new ArrayList<Course>();
        courses.add(new Course(1, "Configure Jersey with annotations"));
        courses.add(new Course(2, "Configure Jersey without web.xml"));
        System.out.println(Thread.currentThread().getName());
        GenericEntity<List<Course>> genCourses = new GenericEntity<List<Course>>(courses) {
        };
        return genCourses;

    }

    public static List<Course> getListCourses() {
        System.out.println("Called");
        List<Course> courses = new ArrayList<Course>();
        courses.add(new Course(1, "Configure Jersey with annotations"));
        courses.add(new Course(2, "Configure Jersey without web.xml"));
        System.out.println(Thread.currentThread().getName());
        return courses;

    }

    @Path("/async")
    @GET
    @Authenticate
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public void fetchAll(@Suspended final AsyncResponse asyncResponse) throws InterruptedException, ExecutionException {


        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println("thread count : " + executorService.isTerminated());
            GenericEntity<List<Course>> upCourses = getCourses();

            asyncResponse.resume(upCourses);
        };
        executorService.execute(runnable);
    }

    @GET
    @Path("/sync")
    @Produces(MediaType.APPLICATION_JSON)

    public List<Course> asyncGet() {
        List<Course> courses = new ArrayList<Course>();
        courses.add(new Course(1, "Configure Jersey with annotations"));
        courses.add(new Course(2, "Configure Jersey without web.xml"));
        return courses;
    }

    @GET
    @Path("/future")
    @Produces(MediaType.APPLICATION_JSON)
    public void futures(@Suspended final AsyncResponse asyncResponse) {
        asyncResponse.register(new CompletionCallback() {
            @Override
            public void onComplete(Throwable throwable) {
                if(throwable == null){
                    numberOfSuccessResponses++;
                    System.out.println("success : "+  numberOfSuccessResponses);
                }else{
                    numberOfFailures++;
                    lastException =throwable;
                }
            }
        });


        asyncResponse.setTimeoutHandler(asyncResponse1 -> asyncResponse1.resume(
                Response.status(Response.Status.REQUEST_TIMEOUT).type(MediaType.APPLICATION_JSON)
                        .entity(new CustomException(408, "Timeout Exception", "Request could not be processed within time", null))
                        .build()
        ));

        asyncResponse.setTimeout(10, TimeUnit.MILLISECONDS);
        CompletableFuture.supplyAsync(() -> getCourses(), executorService)
                .thenAccept(response -> asyncResponse.resume(response));


    }

    @GET
    @Path("/chunked")
    public ChunkedOutput<List<Course>> chunkedAsyncGet() {

        ChunkedOutput<List<Course>> chunkedOutput = new ChunkedOutput<>(GenericType.class); //new GenericType<List<Course>>(){}
        new Thread() {
            public void run() {
                try {
                    List<Course> chunk;

                    while ((chunk = getListCourses()) != null) {
                        chunkedOutput.write(chunk);
                    }
                } catch (IOException e) {
                    // IOException thrown when writing the
                    // chunks of response: should be handled
                } finally {
                    try {
                        chunkedOutput.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // simplified: IOException thrown from
                    // this close() should be handled here...
                }
            }
        }.start();

        // the output will be probably returned even before
        // a first chunk is written by the new thread
        return chunkedOutput;
    }

    @GET
    @Path("/links")
    public Response getLinks() throws URISyntaxException {
        Response r = Response.ok().
                link("http://oracle.com", "parent").
                link(new URI("http://jersey.java.net"), "framework").
                build();
        return r;
    }
}
