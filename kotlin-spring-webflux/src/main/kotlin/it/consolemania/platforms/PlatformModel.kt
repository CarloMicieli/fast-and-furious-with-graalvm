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
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.Link
import org.springframework.hateoas.RepresentationModel
import java.math.BigDecimal
import java.time.Year

data class PlatformModel(
    val platformUrn: URN,
    val name: String,
    val manufacturer: String,
    val generation: Int,
    val type: String,
    val release: Release,
    val discontinuedYear: Year?,
    val discontinued: Boolean,
    val introductoryPrice: BigDecimal,
    val unitsSold: Int,
    val media: Media,
    val techSpecs: TechSpecs,
    val metadata: PlatformMetadata
) : RepresentationModel<PlatformModel>(platformLinks(platformUrn)) {

    companion object {
        fun of(platform: Platform): PlatformModel = PlatformModel(
            platformUrn = platform.platformUrn,
            name = platform.name,
            manufacturer = platform.manufacturer,
            generation = platform.generation,
            type = platform.type,
            release = Release(
                japan = platform.releaseJp,
                europe = platform.releaseEu,
                northAmerica = platform.releaseNa
            ),
            discontinuedYear = platform.discontinuedYear?.let { Year.of(it) },
            discontinued = platform.discontinued,
            introductoryPrice = platform.introductoryPrice,
            unitsSold = platform.unitsSold,
            media = platform.media,
            techSpecs = TechSpecs(
                cpu = platform.cpu,
                display = platform.display,
                memory = platform.memory,
                sound = platform.sound
            ),
            metadata = PlatformMetadata(
                createdDate = platform.createdDate,
                lastModifiedDate = platform.lastModifiedDate,
                version = platform.version
            )
        )

        fun platformLinks(platformUrn: URN): List<Link> {
            return listOf(
                Link.of("/platforms/$platformUrn", IanaLinkRelations.SELF)
            )
        }
    }
}
