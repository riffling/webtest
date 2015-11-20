package com.example;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.dreamsyssoft.demo.rest.model.User;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/user")
public class CustomerManager {

    /**
     * Method handling HTTP GET requests. The returned object will be sent to
     * the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/hello")
    public String hello(String name) {
        return "Got it!+"+name;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON })
    public Customer getCusmtomer(String id) {
        return new Customer();
    }
    @GET
    @Path("user/{user_id}")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    @Consumes(javax.ws.rs.core.MediaType.TEXT_PLAIN)
    public User getUser(@PathParam("user_id") int userId) {
        return new User();
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/child")
    public JSONObject getClichedMessage(JSONObject param) throws JSONException {
        System.out.println(param);
        return param;
    }
}
