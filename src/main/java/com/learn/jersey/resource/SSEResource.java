package com.learn.jersey.resource;

import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;

@Path("/events")
public  class SSEResource {
    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void getServerSentEvents(@Context SseEventSink eventSink, @Context Sse sse) {
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                // ... code that waits 1 second
                final OutboundSseEvent event = sse.newEventBuilder()
                        .name("message-to-client")
                        .data(String.class, "Hello events " + i + "!")
                        .build();
                System.out.println(event.getData());
                eventSink.send(event);
            }
        }).start();
    }
}
