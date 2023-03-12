/*
 *   Copyright (c) 2023 Carlo Micieli
 *
 *    Licensed to the Apache Software Foundation (ASF) under one
 *    or more contributor license agreements.  See the NOTICE file
 *    distributed with this work for additional information
 *    regarding copyright ownership.  The ASF licenses this file
 *    to you under the Apache License, Version 2.0 (the
 *    "License"); you may not use this file except in compliance
 *    with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing,
 *    software distributed under the License is distributed on an
 *    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *    KIND, either express or implied.  See the License for the
 *    specific language governing permissions and limitations
 *    under the License.
 */
package it.consolemania.platforms;

import com.jcabi.urn.URN;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.Status;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import it.consolemania.games.GameModel;
import it.consolemania.games.GamesService;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.validation.Valid;

@Controller("/platforms")
@ExecuteOn(TaskExecutors.IO)
public class PlatformsController {
    private final PlatformsService platformsService;
    private final GamesService gamesService;

    public PlatformsController(PlatformsService platformsService, GamesService gamesService) {
        this.platformsService = platformsService;
        this.gamesService = gamesService;
    }

    @Post(value = "/", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    @Status(HttpStatus.CREATED)
    HttpResponse<Void> postPlatform(@Valid @Body PlatformRequest request) {
        var urn = platformsService.createPlatform(request);
        return PlatformsController.createdPlatform(urn);
    }

    @Put(value = "/{platformUrn}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    @Status(HttpStatus.NO_CONTENT)
    void putPlatform(@PathVariable URN platformUrn, @Valid @Body PlatformRequest request) {
        platformsService.updatePlatform(platformUrn, request);
    }

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    List<PlatformModel> getAllPlatforms() {
        return StreamSupport.stream(platformsService.getAllPlatforms().spliterator(), false)
                .map(PlatformModel::of)
                .collect(Collectors.toList());
    }

    @Get("/{platformUrn}")
    @Produces(MediaType.APPLICATION_JSON)
    PlatformModel getPlatformByUrn(@PathVariable URN platformUrn) {
        return platformsService
                .getPlatformByUrn(platformUrn)
                .map(PlatformModel::of)
                .orElseThrow(() -> new PlatformNotFoundException(platformUrn));
    }

    @Get("/{platformUrn}/games")
    @Produces(MediaType.APPLICATION_JSON)
    List<GameModel> getGamesByPlatformUrn(@PathVariable URN platformUrn) {
        return platformsService
                .getPlatformByUrn(platformUrn)
                .map(platform -> gamesService.getGamesByPlatform(platform.platformId()))
                .map(games -> StreamSupport.stream(games.spliterator(), false))
                .map(games -> games.map(GameModel::of))
                .map(gameModelStream -> gameModelStream.collect(Collectors.toList()))
                .orElse(List.of());
    }

    @NonNull private static MutableHttpResponse<Void> createdPlatform(@NonNull URN platformUrn) {
        return HttpResponse.created(location(platformUrn));
    }

    private static URI location(URN platformUrn) {
        return URI.create("/platforms/" + platformUrn);
    }
}
