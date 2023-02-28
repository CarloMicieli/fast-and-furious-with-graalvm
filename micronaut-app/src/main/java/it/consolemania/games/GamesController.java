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
package it.consolemania.games;

import com.jcabi.urn.URN;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.Status;
import java.net.URI;
import javax.validation.Valid;
import reactor.core.publisher.Mono;

@Controller("/games")
public class GamesController {
    private final GamesService gamesService;

    public GamesController(GamesService gamesService) {
        this.gamesService = gamesService;
    }

    @Post
    @Status(HttpStatus.CREATED)
    Mono<HttpResponse<Void>> postGames(@Valid @Body GameRequest request) {
        return gamesService.createGame(request).map(GamesController::createdGenre);
    }

    @Put("/{gameUrn}")
    @Status(HttpStatus.NO_CONTENT)
    Mono<Void> putGames(@PathVariable URN gameUrn, @Valid @Body GameRequest request) {
        return gamesService.updateGame(gameUrn, request);
    }

    @Delete("/{gameUrn}")
    @Status(HttpStatus.NO_CONTENT)
    Mono<Void> deleteGame(@PathVariable URN gameUrn) {
        return gamesService.deleteGame(gameUrn);
    }

    @Get(value = "/{gameUrn}", produces = MediaType.APPLICATION_JSON)
    Mono<GameModel> getGameByUrn(@PathVariable URN gameUrn) {
        return gamesService.getGameByUrn(gameUrn).map(GameModel::of);
    }

    @NonNull private static MutableHttpResponse<Void> createdGenre(@NonNull URN gameUrn) {
        return HttpResponse.created(location(gameUrn));
    }

    private static URI location(URN gameUrn) {
        return URI.create("/games/" + gameUrn);
    }
}
