package org.example.resource;

import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.example.entity.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    Logger logger = LoggerFactory.getLogger(UserResource.class);
    @Inject
    PgPool client;

    @GET
    @Path("/all")
    @RunOnVirtualThread
    public List<UserInfo> getAll() throws InterruptedException {
        logger.info("Get all users requested");
        Thread.sleep(1000);
        return UserInfo.findAll(client);
    }

    @GET
    @Path("/all/plain")
    public List<UserInfo> getAllPlain() throws InterruptedException {
        Thread.sleep(1000);
        return UserInfo.findAll(client);
    }

    @GET
    @Path("/all/uni")
    public Uni<List<UserInfo>> getAllUni() throws InterruptedException {
        return UserInfo.findAllUni(client);
    }

    @GET
    @Path("/all/multi")
    public Multi<UserInfo> getAllMulti() throws InterruptedException {
        return UserInfo.findAllMulti(client);
    }
}
