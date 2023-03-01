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

import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;
import java.math.BigDecimal;
import java.time.Year;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@RecordBuilder
@Serdeable
public record PlatformRequest(
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Size(max = 100) String manufacturer,
        @Positive Integer generation,
        @NotNull PlatformType type,
        Release release,
        Year discontinuedYear,
        boolean discontinued,
        @Positive BigDecimal introductoryPrice,
        @Positive Integer unitsSold,
        @NotNull Media media,
        TechSpecs techSpecs) {}
