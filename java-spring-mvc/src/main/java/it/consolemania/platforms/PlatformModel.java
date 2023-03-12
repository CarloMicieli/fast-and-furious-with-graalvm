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
package it.consolemania.platforms;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.jcabi.urn.URN;
import it.consolemania.ResourceMetadata;
import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.RepresentationModel;

public final class PlatformModel extends RepresentationModel<PlatformModel> {

    private final URN platformUrn;
    private final String name;
    private final String manufacturer;
    private final Integer generation;
    private final PlatformType type;
    private final Year year;
    private final Release release;
    private final Year discontinuedYear;
    private final boolean discontinued;
    private final BigDecimal introductoryPrice;
    private final Integer unitsSold;
    private final List<Media> media;
    private final TechSpecs techSpecs;
    private final ResourceMetadata metadata;

    private PlatformModel(
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
            ResourceMetadata metadata) {
        super(linkTo(PlatformsController.class).slash(platformUrn).withRel(IanaLinkRelations.SELF));

        this.platformUrn = platformUrn;
        this.name = name;
        this.manufacturer = manufacturer;
        this.generation = generation;
        this.type = type;
        this.year = year;
        this.release = release;
        this.discontinuedYear = discontinuedYear;
        this.discontinued = discontinued;
        this.introductoryPrice = introductoryPrice;
        this.unitsSold = unitsSold;
        this.media = media;
        this.techSpecs = techSpecs;
        this.metadata = metadata;
    }

    public URN getPlatformUrn() {
        return platformUrn;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Integer getGeneration() {
        return generation;
    }

    public PlatformType getType() {
        return type;
    }

    public Release getRelease() {
        return release;
    }

    public Year getDiscontinuedYear() {
        return discontinuedYear;
    }

    public boolean isDiscontinued() {
        return discontinued;
    }

    public BigDecimal getIntroductoryPrice() {
        return introductoryPrice;
    }

    public Integer getUnitsSold() {
        return unitsSold;
    }

    public List<Media> getMedia() {
        return media;
    }

    public TechSpecs getTechSpecs() {
        return techSpecs;
    }

    public ResourceMetadata getMetadata() {
        return metadata;
    }

    public static PlatformModel of(Platform platform) {
        var discountinuedYear =
                Optional.ofNullable(platform.discontinuedYear()).map(Year::of).orElse(null);
        var metadata = new ResourceMetadata(platform.createdDate(), platform.lastModifiedDate(), platform.version());

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
