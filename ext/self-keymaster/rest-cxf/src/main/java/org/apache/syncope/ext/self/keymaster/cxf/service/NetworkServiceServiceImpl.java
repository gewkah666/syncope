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
package org.apache.syncope.ext.self.keymaster.cxf.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.ws.rs.core.Response;
import org.apache.syncope.common.keymaster.client.api.model.NetworkService;
import org.apache.syncope.core.logic.NetworkServiceLogic;
import org.apache.syncope.ext.self.keymaster.api.service.NetworkServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class NetworkServiceServiceImpl implements NetworkServiceService {

    private static final long serialVersionUID = 4160287655489345100L;

    @Autowired
    private NetworkServiceLogic logic;

    @Override
    public List<NetworkService> list(final NetworkService.Type serviceType) {
        return logic.list(serviceType);
    }

    @Override
    public NetworkService get(final NetworkService.Type serviceType) {
        return logic.get(serviceType);
    }

    @PreAuthorize("@environment.getProperty('keymaster.username') == authentication.name and not(isAnonymous())")
    @Override
    public CompletableFuture<Response> register(final NetworkService networkService) {
        return CompletableFuture.supplyAsync(() -> {
            logic.register(networkService);
            return Response.noContent().build();
        });
    }

    @PreAuthorize("@environment.getProperty('keymaster.username') == authentication.name and not(isAnonymous())")
    @Override
    public CompletableFuture<Response> unregister(final NetworkService networkService) {
        return CompletableFuture.supplyAsync(() -> {
            logic.unregister(networkService);
            return Response.noContent().build();
        });
    }
}
