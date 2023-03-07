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
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.Link
import org.springframework.hateoas.RepresentationModel
import java.time.Year
import java.util.UUID

data class GameModel(
    val gameUrn: URN,
    val platformId: UUID,
    val title: String,
    val genres: List<Genre>,
    val modes: List<Mode>,
    val series: String?,
    val developer: String,
    val publisher: String,
    val plot: String?,
    val rating: Rating,
    val year: Year,
    val metadata: GameMetadata
) : RepresentationModel<GameModel>(gameLinks(gameUrn)) {

    companion object {
        fun of(game: Game) = GameModel(
            gameUrn = game.gameUrn,
            platformId = game.platformId,
            title = game.title,
            genres = game.genres,
            modes = game.modes,
            series = game.series,
            developer = game.developer,
            publisher = game.publisher,
            plot = game.plot,
            rating = game.rating,
            year = Year.of(game.year),
            metadata = GameMetadata(
                createdDate = game.createdDate,
                lastModifiedDate = game.lastModifiedDate,
                version = game.version
            )
        )

        fun gameLinks(gameUrn: URN): List<Link> {
            return listOf(
                Link.of("/games/$gameUrn", IanaLinkRelations.SELF)
            )
        }
    }
}
