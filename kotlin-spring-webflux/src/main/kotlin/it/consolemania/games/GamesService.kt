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
import it.consolemania.util.UuidSource
import kotlinx.coroutines.flow.toList
import java.util.UUID

class GamesService(
    private val uuidSource: UuidSource,
    private val gamesRepository: GamesRepository
) {
    suspend fun createGame(gameRequest: GameRequest): URN {
        val newGame = entityFromRequest(gameRequest, null)
        gamesRepository.save(newGame)
        return newGame.gameUrn
    }

    suspend fun updateGame(gameUrn: URN, gameRequest: GameRequest) {
        val game = entityFromRequest(gameRequest, gamesRepository.findByGameUrn(gameUrn))
        gamesRepository.save(game)
    }

    private fun entityFromRequest(game: GameRequest, gameEntity: Game?): Game = TODO()

    suspend fun deleteGame(gameUrn: URN) {
        gamesRepository.deleteByGameUrn(gameUrn)
    }

    suspend fun getGamesByPlatform(platformId: UUID): List<Game> {
        return gamesRepository.findAllByPlatformId(platformId).toList()
    }

    suspend fun getGameByUrn(gameURN: URN): Game? {
        return gamesRepository.findByGameUrn(gameURN)
    }
}
