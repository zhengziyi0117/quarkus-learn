package org.example.resource;

import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.groups.UniCreate;
import io.smallrye.mutiny.helpers.BlockingIterable;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.example.entity.UserInfo;

import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@RunOnVirtualThread
public class UserResource {

    @Inject
    PgPool client;

    @GET
    @Path("/all")
    public List<UserInfo> getAll() {
        return UserInfo.findAll(client);
    }
}
