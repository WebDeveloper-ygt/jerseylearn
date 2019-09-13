package com.learn.jersey.test;

import com.learn.jersey.filters.ClientRequestFilterImpl;
import com.learn.jersey.interceptors.GZIPReaderInterceptor;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ClientSideTest {

    public static void main(String [] args){

        Client client = ClientBuilder.newClient();
        /*WebTarget target = client.target("https://localhost://8080/web/api/custom/date/today");

        target.register(new ClientRequestFilterImpl());
        Invocation invocation = target.request("application/date").header("Authorization", "something").buildGet();

        Response invoke = invocation.invoke();
        System.out.println(invoke);*/

        WebTarget target = client.target("https://localhost://8080/web/api/courses/sync");
        target.register(new GZIPReaderInterceptor());
        Invocation invocation = target.request(MediaType.APPLICATION_JSON).buildGet();
        Response invoke = invocation.invoke();
        System.out.println(invoke);
    }
}
