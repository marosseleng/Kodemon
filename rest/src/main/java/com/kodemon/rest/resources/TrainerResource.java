package com.kodemon.rest.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by mseleng on 12/11/16.
 */
@Path("trainers")
public class TrainerResource {

    private static final Logger LOG = LoggerFactory.getLogger(TrainerResource.class);

    @GET
    @Produces("application/json")
    public Response all() {
        return Response.serverError().build();
    }
}
