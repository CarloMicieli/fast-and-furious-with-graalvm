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
package it.consolemania.platforms;

import com.jcabi.urn.URN;
import it.consolemania.util.UuidSource;
import jakarta.inject.Singleton;
import java.time.Year;
import java.util.Optional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Singleton
public class PlatformsService {

    private final UuidSource uuidSource;
    private final PlatformsRepository platformsRepository;

    public PlatformsService(UuidSource uuidSource, PlatformsRepository platformsRepository) {
        this.uuidSource = uuidSource;
        this.platformsRepository = platformsRepository;
    }

    public Mono<URN> createPlatform(PlatformRequest request) {
        var newPlatform = entityFromRequest(request, null);
        return platformsRepository.save(newPlatform)
            .map(i -> newPlatform.platformUrn());
    }

    public Mono<Void> updatePlatform(URN platformUrn, PlatformRequest request) {
        return platformsRepository.findByPlatformUrn(platformUrn)
            .flatMap(platform -> {
                var entity = entityFromRequest(request, platform);
                return platformsRepository.update(entity);
            })
            .then();
    }

    public Flux<Platform> getAllPlatforms() {
        return platformsRepository.findAll();
    }

    public Mono<Platform> getPlatformByUrn(URN platformUrn) {
        return platformsRepository.findByPlatformUrn(platformUrn);
    }

    Platform entityFromRequest(PlatformRequest platform, Platform entity) {
        var platformUrn = PlatformURN.of(platform.name());

        var discontinuedYear = Optional.ofNullable(platform.discontinuedYear())
                .map(Year::getValue)
                .orElse(null);

        var existingPlatform = Optional.ofNullable(entity);

        return new Platform(
                existingPlatform.map(Platform::platformId).orElseGet(uuidSource::generateNewId),
                platformUrn,
                platform.name(),
                platform.manufacturer(),
                platform.generation(),
                platform.type().name(),
                platform.release(),
                discontinuedYear,
                platform.discontinued(),
                platform.introductoryPrice(),
                platform.unitsSold(),
                platform.media(),
                platform.techSpecs(),
                existingPlatform.map(Platform::createdDate).orElse(null),
                existingPlatform.map(Platform::lastModifiedDate).orElse(null),
                existingPlatform.map(Platform::version).orElse(null));
    }
}
