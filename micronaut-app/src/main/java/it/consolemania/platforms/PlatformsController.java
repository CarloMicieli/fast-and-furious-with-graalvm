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
import it.consolemania.games.GameModel;
import it.consolemania.games.GamesService;
import java.net.URI;
import javax.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller("/platforms")
public class PlatformsController {
    private final PlatformsService platformsService;
    private final GamesService gamesService;

    public PlatformsController(PlatformsService platformsService, GamesService gamesService) {
        this.platformsService = platformsService;
        this.gamesService = gamesService;
    }

    @Post
    @Status(HttpStatus.CREATED)
    Mono<HttpResponse<Void>> postPlatform(@Valid @Body PlatformRequest request) {
        return platformsService.createPlatform(request).map(PlatformsController::createdPlatform);
    }

    @Put("/{platformUrn")
    @Status(HttpStatus.NO_CONTENT)
    Mono<Void> putPlatform(@PathVariable URN platformUrn, @Valid @Body PlatformRequest request) {
        return platformsService.updatePlatform(platformUrn, request);
    }

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    Flux<PlatformModel> getAllPlatforms() {
        return platformsService.getAllPlatforms().map(PlatformModel::of);
    }

    @Get("/{platformUrn}")
    @Produces(MediaType.APPLICATION_JSON)
    Mono<PlatformModel> getPlatformByUrn(@PathVariable URN platformUrn) {
        return platformsService.getPlatformByUrn(platformUrn).map(PlatformModel::of);
    }

    @Get("/{platformUrn}/games")
    @Produces(MediaType.APPLICATION_JSON)
    Flux<GameModel> getGamesByPlatformUrn(@PathVariable URN platformUrn) {
        return platformsService
                .getPlatformByUrn(platformUrn)
                .flux()
                .flatMap(platform -> gamesService.getGamesByPlatform(platform.platformId()))
                .map(GameModel::of);
    }

    @NonNull private static MutableHttpResponse<Void> createdPlatform(@NonNull URN platformUrn) {
        return HttpResponse.created(location(platformUrn));
    }

    private static URI location(URN platformUrn) {
        return URI.create("/platforms/" + platformUrn);
    }
}
