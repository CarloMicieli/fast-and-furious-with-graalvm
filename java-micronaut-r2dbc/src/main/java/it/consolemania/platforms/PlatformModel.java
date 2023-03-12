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
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@Serdeable(naming = SnakeCaseStrategy.class)
public record PlatformModel(
        URN platformUrn,
        String name,
        String manufacturer,
        Integer generation,
        PlatformType type,
        Year year,
        Release release,
        Year discontinuedYear,
        boolean discontinued,
        BigDecimal introductoryPrice,
        Integer unitsSold,
        List<Media> media,
        TechSpecs techSpecs,
        PlatformMetadata metadata) {

    public static PlatformModel of(Platform platform) {
        var discountinuedYear =
                Optional.ofNullable(platform.discontinuedYear()).map(Year::of).orElse(null);
        var metadata = new PlatformMetadata(platform.createdDate(), platform.lastModifiedDate(), platform.version());

        return new PlatformModel(
                platform.platformUrn(),
                platform.name(),
                platform.manufacturer(),
                platform.generation(),
                PlatformType.valueOf(platform.type()),
                Year.of(platform.year()),
                platform.release(),
                discountinuedYear,
                platform.discontinued(),
                platform.introductoryPrice(),
                platform.unitsSold(),
                platform.media(),
                platform.techSpecs(),
                metadata);
    }
}
