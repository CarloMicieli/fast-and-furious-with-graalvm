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

import com.fasterxml.jackson.annotation.JsonProperty;
import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

@RecordBuilder
public record PlatformRequest(
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Size(max = 100) String manufacturer,
        @Positive Integer generation,
        @NotNull PlatformType type,
        @NotNull Year year,
        Release release,
        @JsonProperty("discontinued_year") Year discontinuedYear,
        boolean discontinued,
        @Positive @JsonProperty("introductory_price") BigDecimal introductoryPrice,
        @Positive @JsonProperty("units_sold") Integer unitsSold,
        @NotNull List<Media> media,
        @JsonProperty("tech_specs") TechSpecs techSpecs)
        implements PlatformRequestBuilder.With {}
