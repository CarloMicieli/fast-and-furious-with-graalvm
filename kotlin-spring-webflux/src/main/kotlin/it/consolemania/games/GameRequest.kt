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
package it.consolemania.games

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.Year

data class GameRequest(
    @NotBlank
    @Size(max = 100)
    val title: String,
    @NotNull val genres: List<Genre>,
    @NotBlank
    @Size(max = 100)
    val platform: String,
    @NotNull val modes: List<Mode>,
    @Size(max = 100) val series: String?,
    @NotBlank
    @Size(max = 250)
    val developer: String,
    @NotBlank
    @Size(max = 250)
    val publisher: String,
    val rating: Rating,
    @Size(max = 2500) val plot: String?,
    val year: Year
)
