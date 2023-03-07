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
package it.consolemania.games

import com.jcabi.urn.URN
import it.consolemania.platforms.PlatformNotFoundException
import it.consolemania.platforms.PlatformService
import it.consolemania.util.UuidSource
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class GamesService(
    private val uuidSource: UuidSource,
    private val gamesRepository: GamesRepository,
    private val platformsService: PlatformService
) {
    suspend fun createGame(gameRequest: GameRequest): URN {
        val newGame = entityFromRequest(gameRequest, null)
        if (gamesRepository.existsByGameUrn(newGame.gameUrn)) {
            throw GameAlreadyExistsException(newGame.gameUrn)
        }

        gamesRepository.save(newGame)
        return newGame.gameUrn
    }

    suspend fun updateGame(gameUrn: URN, gameRequest: GameRequest) {
        val game = entityFromRequest(gameRequest, gamesRepository.findByGameUrn(gameUrn))
        gamesRepository.save(game)
    }

    private suspend fun entityFromRequest(game: GameRequest, gameEntity: Game?): Game {
        val platform = platformsService.getPlatformByName(game.platform) ?: throw PlatformNotFoundException(
            game.platform
        )
        val gameUrn = GameURN.of(game.platform, game.title)

        return Game(
            gameEntity?.gameId ?: uuidSource.generateNewId(),
            gameUrn,
            platform.platformId,
            game.title,
            game.genres,
            game.modes,
            game.series,
            game.developer,
            game.publisher,
            game.plot,
            game.rating,
            game.year.value,
            gameEntity?.createdDate,
            gameEntity?.lastModifiedDate,
            gameEntity?.version
        )
    }

    suspend fun deleteGame(gameUrn: URN) =
        gamesRepository.deleteByGameUrn(gameUrn)

    suspend fun getGamesByPlatform(platformId: UUID): Flow<Game> =
        gamesRepository.findAllByPlatformId(platformId)

    suspend fun getGameByUrn(gameURN: URN): Game? =
        gamesRepository.findByGameUrn(gameURN)
}
