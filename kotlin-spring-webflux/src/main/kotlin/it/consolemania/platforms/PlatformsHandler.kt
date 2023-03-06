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
import it.consolemania.games.GamesService
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
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
        val platformRequest = request.awaitBodyOrNull<PlatformRequest>()!!
        val newUrn = platformService.createPlatform(platformRequest)
        return ServerResponse.created(URI.create("/platforms/$newUrn")).buildAndAwait()
    }

    suspend fun putPlatform(request: ServerRequest): ServerResponse {
        val platformUrn = URN.create(request.pathVariable("platformUrn"))
        val platformRequest = request.awaitBodyOrNull<PlatformRequest>()!!

        val result = platformService.getPlatformByUrn(platformUrn)
        if (result != null) {
            platformService.updatePlatform(platformUrn, platformRequest)
        }

        return ServerResponse.noContent().buildAndAwait()
    }

    suspend fun getAllPlatforms(request: ServerRequest): ServerResponse {
        val result = platformService.getAllPlatforms()
        val body = CollectionModel.of(result)
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(body)
    }

    suspend fun getPlatformByUrn(request: ServerRequest): ServerResponse {
        val platformUrn = URN.create(request.pathVariable("platformUrn"))
        val platform = platformService.getPlatformByUrn(platformUrn)
        val body = EntityModel.of(platform!!, emptyList())
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(body)
    }

    suspend fun getGamesByPlatformUrn(request: ServerRequest): ServerResponse {
        val platformUrn = URN.create(request.pathVariable("platformUrn"))
        val platform = platformService.getPlatformByUrn(platformUrn)
        val result = gamesService.getGamesByPlatform(platform!!.platformId)
        val body = CollectionModel.of(result)
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(body)
    }
}
