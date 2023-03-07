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
package it.consolemania.platforms

import com.jcabi.urn.URN
import it.consolemania.BadRequestException
import it.consolemania.games.GameModel
import it.consolemania.games.GamesService
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.hateoas.CollectionModel
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBodyOrNull
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait
import java.net.URI

class PlatformsHandler(
    private val platformService: PlatformService,
    private val gamesService: GamesService
) {

    suspend fun postPlatform(request: ServerRequest): ServerResponse {
        val platformRequest = request.awaitBodyOrNull<PlatformRequest>() ?: throw BadRequestException(
            "Empty platform request"
        )
        val newUrn = platformService.createPlatform(platformRequest)
        return ServerResponse.created(URI.create("/platforms/$newUrn")).buildAndAwait()
    }

    suspend fun putPlatform(request: ServerRequest): ServerResponse {
        val platformUrn = URN.create(request.pathVariable("platformUrn"))
        val platformRequest = request.awaitBodyOrNull<PlatformRequest>() ?: throw BadRequestException(
            "Empty platform request"
        )

        val result = platformService.getPlatformByUrn(platformUrn)
        if (result != null) {
            platformService.updatePlatform(platformUrn, platformRequest)
        }

        return ServerResponse.noContent().buildAndAwait()
    }

    suspend fun getAllPlatforms(@Suppress("UNUSED_PARAMETER") request: ServerRequest): ServerResponse {
        val result = platformService.getAllPlatforms().map { PlatformModel.of(it) }.toList()
        val body = CollectionModel.of(result)
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(body)
    }

    suspend fun getPlatformByUrn(request: ServerRequest): ServerResponse {
        val platformUrn = URN.create(request.pathVariable("platformUrn"))
        val platform = platformService.getPlatformByUrn(platformUrn)
        return if (platform != null) {
            val body = PlatformModel.of(platform)
            ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(body)
        } else {
            ServerResponse.notFound().buildAndAwait()
        }
    }

    suspend fun getGamesByPlatformUrn(request: ServerRequest): ServerResponse {
        val platformUrn = URN.create(request.pathVariable("platformUrn"))
        val platform = platformService.getPlatformByUrn(platformUrn)
        return if (platform != null) {
            val gamesList = gamesService.getGamesByPlatform(platform.platformId).map { GameModel.of(it) }.toList()
            val body = CollectionModel.of(gamesList)
            ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(body)
        } else {
            ServerResponse.notFound().buildAndAwait()
        }
    }
}
