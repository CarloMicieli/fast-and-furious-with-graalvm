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
import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.DateUpdated;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Version;
import io.micronaut.serde.annotation.Serdeable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.Embedded;

@Serdeable
@MappedEntity("platforms")
public record Platform(
        @Id UUID platformId,
        URN platformUrn,
        String name,
        String manufacturer,
        Integer generation,
        String type,
        @Embedded Release release,
        Integer discontinuedYear,
        boolean discontinued,
        BigDecimal introductoryPrice,
        Integer unitsSold,
        Media media,
        @Embedded TechSpecs techSpecs,
        @DateCreated Instant createdDate,
        @DateUpdated Instant lastModifiedDate,
        @Version Integer version) {}
