package com.ihorpolataiko.dropwizardauth.resource;

import com.ihorpolataiko.dropwizardauth.dao.UserDao;
import io.dropwizard.jersey.PATCH;
import io.dropwizard.validation.Validated;
import io.swagger.annotations.Api;
import transfer.ExistingRecord;
import transfer.UserDto;
import transfer.validation.NewRecord;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

import static transfer.UserDto.toUser;

@Path("/users")
@Produces(MediaType.TEXT_PLAIN)
@Api("Users")
public class UserResource {

    private final UserDao userDao;

    @Inject
    public UserResource(UserDao userDao) {
        this.userDao = userDao;
    }

    @Path("/{id}")
    @GET
    public Response findById(@PathParam("id") String id) {
        return Response.ok(userDao.findById(id)).build();
    }

    @Path("/username/{username}")
    @GET
    public Response findByUsername(@PathParam("username") String username) {
        return Response.ok(userDao.findByUsername(username)).build();
    }

    @GET
    public Response findAll() {
        return Response.ok(userDao.findAll()).build();
    }

    @POST
    public Response create(@Validated(NewRecord.class) UserDto user) {
        return Response.ok(userDao.create(toUser(user))).status(Response.Status.CREATED).build();
    }

    @Path("/{id}")
    @PUT
    public Response update(@PathParam("id") String id, @Validated(ExistingRecord.class) UserDto user) {
        user.setId(id);
        userDao.update(toUser(user));
        return Response.ok().build();
    }

    @Path("/{id}")
    @PATCH
    public Response updateFields(String id, Map<String, Object> fieldsToUpdate) {
        userDao.updateFields(id, fieldsToUpdate);
        return Response.ok().build();
    }

    @Path("/{id}")
    @DELETE
    public Response deleteById(@PathParam("id") String id) {
        userDao.deleteById(id);
        return Response.noContent().build();
    }
}
