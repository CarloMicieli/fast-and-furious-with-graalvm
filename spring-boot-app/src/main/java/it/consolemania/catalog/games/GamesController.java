/*
 *   Copyright (c) 2022-2023 Carlo Micieli
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
package it.consolemania.catalog.games;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.jcabi.urn.URN;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
public class GamesController {

    private final GamesService gamesService;

    public GamesController(GamesService gamesService) {
        this.gamesService = gamesService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Void> postGame(@RequestBody @Valid GameRequest newGame) {
        var gameUrn = gamesService.createGame(newGame);
        return ResponseEntity.created(
                        linkTo(GamesController.class).slash(gameUrn).toUri())
                .build();
    }

    @PutMapping("/{gameUrn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void putGame(@PathVariable URN gameUrn, @RequestBody @Valid GameRequest gameRequest) {
        gamesService.updateGame(gameUrn, gameRequest);
    }

    @DeleteMapping("/{gameUrn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteGame(@PathVariable URN gameUrn) {
        gamesService.deleteGame(gameUrn);
    }

    @GetMapping("/{gameUrn}")
    ResponseEntity<GameModel> getGameByUrn(@PathVariable URN gameUrn) {
        return gamesService
                .getGameByUrn(gameUrn)
                .map(GameModel::of)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new GameNotFoundException(gameUrn));
    }
}
