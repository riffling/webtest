package com.example;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.dreamsyssoft.demo.rest.model.User;

/**
 * Root resource (exposed at "myresource" path)
 */
@javax.ws.rs.Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent to
     * the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("xx")
    @Produces({ javax.ws.rs.core.MediaType.TEXT_PLAIN, javax.ws.rs.core.MediaType.APPLICATION_JSON })
    @Consumes(javax.ws.rs.core.MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }

    @GET
    @Path("user/{user_id}")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    @Consumes(javax.ws.rs.core.MediaType.TEXT_PLAIN)
    public User getUser(@PathParam("user_id") int userId) {
        return new User();
    }
}
