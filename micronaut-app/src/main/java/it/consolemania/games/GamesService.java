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
import it.consolemania.platforms.Platform;
import it.consolemania.platforms.PlatformsRepository;
import it.consolemania.util.UuidSource;
import jakarta.inject.Singleton;
import java.util.Optional;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Singleton
public class GamesService {

    private final UuidSource uuidSource;
    private final GamesRepository gamesRepository;
    private final PlatformsRepository platformsRepository;

    public GamesService(
            UuidSource uuidSource, GamesRepository gamesRepository, PlatformsRepository platformsRepository) {
        this.uuidSource = uuidSource;
        this.gamesRepository = gamesRepository;
        this.platformsRepository = platformsRepository;
    }

    public Mono<URN> createGame(GameRequest request) {
        return entityFromRequest(request, null).flatMap(gamesRepository::save).map(Game::gameUrn);
    }

    public Mono<Void> updateGame(URN gameUrn, GameRequest request) {
        return gamesRepository
                .findByGameUrn(gameUrn)
                .flatMap(existingGame -> entityFromRequest(request, existingGame))
                .flatMap(gamesRepository::update)
                .then();
    }

    public Mono<Void> deleteGame(URN gameUrn) {
        return gamesRepository.deleteByGameUrn(gameUrn).then();
    }

    public Flux<Game> getGamesByPlatform(UUID platformId) {
        return gamesRepository.findByPlatformId(platformId);
    }

    public Mono<Game> getGameByUrn(URN gameUrn) {
        return gamesRepository.findByGameUrn(gameUrn);
    }

    Mono<Game> entityFromRequest(GameRequest game, Game gameEntity) {
        return platformsRepository
                .findByName(game.platform())
                .map(Platform::platformId)
                .map(platformId -> {
                    var gameUrn = GameURN.of(game.platform(), game.title());
                    var existingGame = Optional.ofNullable(gameEntity);

                    return new Game(
                            existingGame.map(Game::gameId).orElseGet(uuidSource::generateNewId),
                            gameUrn,
                            platformId,
                            game.title(),
                            game.genres(),
                            game.modes(),
                            game.series(),
                            game.developer(),
                            game.publisher(),
                            game.plot(),
                            game.rating(),
                            game.year().getValue(),
                            existingGame.map(Game::createdDate).orElse(null),
                            existingGame.map(Game::lastModifiedDate).orElse(null),
                            existingGame.map(Game::version).orElse(null));
                });
    }
}
