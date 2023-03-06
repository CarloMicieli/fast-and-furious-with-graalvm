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
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.Link
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBodyOrNull
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait
import java.net.URI

class GamesHandler(private val gamesService: GamesService) {

    suspend fun postGame(request: ServerRequest): ServerResponse {
        val gameRequest = request.awaitBodyOrNull<GameRequest>()!!
        val gameUrn = gamesService.createGame(gameRequest)
        return ServerResponse.created(URI.create("/games/$gameUrn")).buildAndAwait()
    }

    suspend fun putGame(request: ServerRequest): ServerResponse {
        val gameUrn = URN.create(request.pathVariable("gameUrn"))
        val gameRequest = request.awaitBodyOrNull<GameRequest>()!!

        val game = gamesService.getGameByUrn(gameUrn)
        if (game != null) {
            gamesService.updateGame(gameUrn, gameRequest)
        }

        return ServerResponse.noContent().buildAndAwait()
    }

    suspend fun deleteGame(request: ServerRequest): ServerResponse {
        val gameUrn = URN.create(request.pathVariable("gameUrn"))
        gamesService.deleteGame(gameUrn)
        return ServerResponse.noContent().buildAndAwait()
    }

    suspend fun getGameByUrn(request: ServerRequest): ServerResponse {
        val gameUrn = URN.create(request.pathVariable("gameUrn"))
        val game = gamesService.getGameByUrn(gameUrn)

        return if (game != null) {
            val self = Link.of("/games/$gameUrn", IanaLinkRelations.SELF)
            val body = EntityModel.of(game, self)
            ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(body)
        } else {
            ServerResponse.notFound().buildAndAwait()
        }
    }
}
