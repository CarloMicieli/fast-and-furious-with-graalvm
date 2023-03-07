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
package it.consolemania.platforms

import com.jcabi.urn.URN
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Embedded
import org.springframework.data.relational.core.mapping.Embedded.OnEmpty
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@Table("platforms")
data class Platform(
    @Id val platformId: UUID,
    val platformUrn: URN,
    val name: String,
    val manufacturer: String,
    val generation: Int,
    val type: String,
    @Embedded(onEmpty = OnEmpty.USE_NULL) val release: Release,
    val discontinuedYear: Int?,
    val discontinued: Boolean,
    val introductoryPrice: BigDecimal,
    val unitsSold: Int,
    val media: Media,
    @Embedded(onEmpty = OnEmpty.USE_NULL) val techSpecs: TechSpecs,
    @CreatedDate val createdDate: Instant?,
    @LastModifiedDate val lastModifiedDate: Instant?,
    @Version val version: Int?
)
