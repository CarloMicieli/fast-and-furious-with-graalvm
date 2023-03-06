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
import io.micronaut.serde.annotation.Serdeable;
import java.time.Year;
import java.util.List;

@Serdeable
public record GameModel(
        URN gameUrn,
        String title,
        List<Genre> genres,
        List<Mode> modes,
        String series,
        String developer,
        String publisher,
        String plot,
        Rating rating,
        Year year) {

    public static GameModel of(Game game) {
        return new GameModel(
                game.gameUrn(),
                game.title(),
                game.genres(),
                game.modes(),
                game.series(),
                game.developer(),
                game.publisher(),
                game.plot(),
                game.rating(),
                Year.of(game.year()));
    }
}
