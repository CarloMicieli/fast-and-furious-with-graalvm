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

import com.jcabi.urn.URN
import jakarta.validation.constraints.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant
import java.util.UUID

@Table("games")
data class Game(
    @Id val gameId: UUID,
    val gameUrn: URN,
    val platformId: UUID,
    val title: String,
    val genres: List<Genre>,
    val modes: List<Mode>,
    val series: String?,
    val developer: String,
    val publisher: String,
    val plot: String?,
    val rating: Rating,
    @NotNull val year: Int,
    @CreatedDate val createdDate: Instant?,
    @LastModifiedDate val lastModifiedDate: Instant?,
    @Version val version: Int?
)
