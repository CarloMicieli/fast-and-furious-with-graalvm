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
package it.consolemania.catalog.platforms;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.jcabi.urn.URN;
import it.consolemania.catalog.games.GameModel;
import it.consolemania.catalog.games.GamesService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/platforms")
public class PlatformsController {

    private final PlatformsService platformsService;
    private final GamesService gamesService;

    public PlatformsController(PlatformsService platformsService, GamesService gamesService) {
        this.platformsService = platformsService;
        this.gamesService = gamesService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Void> postPlatform(@RequestBody @Valid PlatformRequest newPlatform) {
        var platformUrn = platformsService.createPlatform(newPlatform);
        return ResponseEntity.created(
                        linkTo(PlatformsController.class).slash(platformUrn).toUri())
                .build();
    }

    @PutMapping("/{platformUrn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void putPlatform(@PathVariable URN platformUrn, @RequestBody @Valid PlatformRequest updatePlatform) {
        platformsService.updatePlatform(platformUrn, updatePlatform);
    }

    @GetMapping
    ResponseEntity<List<PlatformModel>> getAllPlatforms() {
        var platforms = StreamSupport.stream(platformsService.getAllPlatforms().spliterator(), false)
                .map(PlatformModel::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok(platforms);
    }

    @GetMapping("/{platformUrn}")
    ResponseEntity<PlatformModel> getPlatformByUrn(@PathVariable URN platformUrn) {
        return platformsService
                .getPlatformByUrn(platformUrn)
                .map(PlatformModel::of)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new PlatformNotFoundException(platformUrn));
    }

    @GetMapping("/{platformUrn}/games")
    ResponseEntity<List<GameModel>> getGamesByPlatformUrn(@PathVariable URN platformUrn) {
        return platformsService
                .getPlatformByUrn(platformUrn)
                .map(platform -> gamesService.getGamesByPlatform(platform.platformId()))
                .map(gamesIt -> StreamSupport.stream(gamesIt.spliterator(), false)
                        .map(GameModel::of)
                        .collect(Collectors.toList()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.ok(List.of()));
    }
}
