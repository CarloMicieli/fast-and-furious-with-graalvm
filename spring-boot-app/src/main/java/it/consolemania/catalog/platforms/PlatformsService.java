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

import com.jcabi.urn.URN;
import it.consolemania.catalog.util.UuidSource;
import java.time.Year;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PlatformsService {

    private final PlatformsRepository platforms;
    private final UuidSource uuidSource;

    public PlatformsService(PlatformsRepository platforms, UuidSource uuidSource) {
        this.platforms = platforms;
        this.uuidSource = uuidSource;
    }

    public URN createPlatform(PlatformRequest newPlatform) {
        var platformEntity = entityFromRequest(newPlatform, null);

        if (platforms.existsByPlatformUrn(platformEntity.platformUrn())) {
            throw new PlatformAlreadyExistsException(platformEntity.platformUrn());
        }

        platforms.save(platformEntity);
        return platformEntity.platformUrn();
    }

    public void updatePlatform(URN platformUrn, PlatformRequest platform) {
        platforms.findByPlatformUrn(platformUrn).map(existingPlatform -> {
            var entity = entityFromRequest(platform, existingPlatform);
            return platforms.save(entity);
        });
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

    public Iterable<Platform> getAllPlatforms() {
        return platforms.findAll();
    }

    public Optional<Platform> getPlatformByUrn(URN platformUrn) {
        return platforms.findByPlatformUrn(platformUrn);
    }
}
