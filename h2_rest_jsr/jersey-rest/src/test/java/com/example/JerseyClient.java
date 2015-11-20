package com.example;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.client.ClientConfig;

public class JerseyClient {
    public static void main(String[] argc) throws JSONException {
        ClientConfig clientConfig = new ClientConfig();
//        clientConfig.register(MyClientResponseFilter.class);
//        clientConfig.register(new AnotherClientFilter());
         
        Client client = ClientBuilder.newClient(clientConfig);
//        client.register(ThirdClientFilter.class);
         
        WebTarget webTarget = client.target(Main.BASE_URI);
//        webTarget.register(FilterForExampleCom.class);
        WebTarget resourceWebTarget = webTarget.path("/user");
        WebTarget helloworldWebTarget = resourceWebTarget.path("/hello");
        WebTarget helloworldWebTargetWithQueryParam =
                helloworldWebTarget.queryParam("greeting", "Hi World!");
         
        Invocation.Builder invocationBuilder =
                helloworldWebTargetWithQueryParam.request(MediaType.TEXT_PLAIN_TYPE);
        invocationBuilder.header("some-header", "true");
         
        Response response = invocationBuilder.get();
        System.out.println(response.getStatus());
        System.out.println(response.readEntity(String.class));

        
        // WebResource r = c.resource("http://localhost:9998/helloworld");
//        JSONObject obj = new JSONObject();
//        obj.put("a", "1");
//        obj.put("b", "2");
//        JSONObject response = r.type(MediaType.APPLICATION_JSON_TYPE).post(JSONObject.class, obj);
//        System.out.println(response.toString());
    }
}
