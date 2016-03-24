/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.syncope.common.rest.api.service;

import javax.validation.constraints.NotNull;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.syncope.common.lib.patch.StatusPatch;
import org.apache.syncope.common.lib.patch.UserPatch;
import org.apache.syncope.common.lib.to.PagedResult;
import org.apache.syncope.common.lib.to.UserTO;
import org.apache.syncope.common.rest.api.beans.AnyListQuery;

/**
 * REST operations for users.
 */
@Path("users")
public interface UserService extends AnyService<UserTO, UserPatch> {

    /**
     * Gives the username for the provided user key.
     *
     * @param key user key
     * @return Response object featuring HTTP header with username matching the given key
     */
    @OPTIONS
    @Path("{key}/username")
    Response getUsername(@NotNull @PathParam("key") Long key);

    /**
     * Gives the user key for the provided username.
     *
     * @param username username
     * @return Response object featuring HTTP header with key matching the given username
     */
    @OPTIONS
    @Path("{username}/key")
    Response getUserKey(@NotNull @PathParam("username") String username);

    /**
     * Returns a paged list of existing users matching the given query.
     *
     * @param listQuery query conditions
     * @return paged list of existing users matching the given query
     */
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    PagedResult<UserTO> list(@BeanParam AnyListQuery listQuery);

    /**
     * Creates a new user.
     *
     * @param userTO user to be created
     * @param storePassword whether password shall be stored internally
     * @return Response object featuring Location header of created user as well as the user itself
     * enriched with propagation status information - ProvisioningResult as Entity
     */
    @POST
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    Response create(
            @NotNull UserTO userTO,
            @DefaultValue("true") @QueryParam("storePassword") boolean storePassword);

    /**
     * Performs a status update on given.
     *
     * @param statusPatch status update details
     * @return Response object featuring the updated user enriched with propagation status information
     * - ProvisioningResult as Entity
     */
    @POST
    @Path("{key}/status")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    Response status(@NotNull StatusPatch statusPatch);
}
