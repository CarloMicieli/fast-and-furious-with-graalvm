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

import it.consolemania.platforms.PlatformService
import it.consolemania.util.UuidSource
import org.springframework.context.support.beans
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

object Games {
    val beans = beans {
        bean {
            val gamesService = ref<GamesService>()
            GamesHandler(gamesService)
        }

        bean {
            val uuidSource = ref<UuidSource>()
            val gamesRepository = ref<GamesRepository>()
            val platformService = ref<PlatformService>()
            GamesService(uuidSource, gamesRepository, platformService)
        }

        bean {
            val gamesHandler = ref<GamesHandler>()
            routes(gamesHandler)
        }
    }

    private fun routes(
        gamesHandler: GamesHandler
    ): RouterFunction<ServerResponse> = coRouter {
        "/games".nest {
            accept(MediaType.APPLICATION_JSON).nest {
                POST("", gamesHandler::postGame)
                "{gameUrn}".nest {
                    DELETE("", gamesHandler::deleteGame)
                    PUT("", gamesHandler::putGame)
                }
            }

            GET("{gameUrn}", gamesHandler::getGameByUrn)
        }
    }
}
