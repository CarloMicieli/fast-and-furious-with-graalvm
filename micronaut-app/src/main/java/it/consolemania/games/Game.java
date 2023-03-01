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
import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.DateUpdated;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.annotation.Version;
import io.micronaut.data.model.DataType;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import it.consolemania.config.URNAttributeConverter;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.persistence.Embedded;

@MappedEntity("games")
@Serdeable(naming = SnakeCaseStrategy.class)
public record Game(
        @Id UUID gameId,
        @TypeDef(type = DataType.STRING, converter = URNAttributeConverter.class) URN gameUrn,
        UUID platformId,
        String title,
        List<Genre> genres,
        List<Mode> modes,
        String series,
        String developer,
        String publisher,
        @Embedded Release release,
        Integer year,
        @DateCreated Instant createdDate,
        @DateUpdated Instant lastModifiedDate,
        @Version Integer version) {}
