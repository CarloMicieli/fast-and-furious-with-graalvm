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
import it.consolemania.util.UuidSource
import kotlinx.coroutines.flow.Flow

class PlatformService(private val uuidSource: UuidSource, private val platformsRepository: PlatformsRepository) {
    suspend fun createPlatform(platform: PlatformRequest): URN {
        val newPlatform = entityFromRequest(platform, null)
        if (platformsRepository.existsByPlatformUrn(newPlatform.platformUrn)) {
            throw PlatformAlreadyExistsException(newPlatform.platformUrn)
        }

        platformsRepository.save(newPlatform)
        return newPlatform.platformUrn
    }

    suspend fun updatePlatform(platformUrn: URN, updateRequest: PlatformRequest) {
        val platform = entityFromRequest(updateRequest, platformsRepository.findFirstByPlatformUrn(platformUrn))
        platformsRepository.save(platform)
    }

    private fun entityFromRequest(platform: PlatformRequest, existingPlatform: Platform?): Platform {
        val platformUrn = PlatformURN.of(platform.name)

        val discontinuedYear: Int? = platform.discontinuedYear?.value

        return Platform(
            existingPlatform?.platformId ?: uuidSource.generateNewId(),
            platformUrn,
            platform.name,
            platform.manufacturer,
            platform.generation,
            platform.type.name,
            platform.year.value,
            platform.release.europe,
            platform.release.japan,
            platform.release.northAmerica,
            discontinuedYear,
            platform.discontinued,
            platform.introductoryPrice,
            platform.unitsSold,
            platform.media,
            platform.techSpecs.cpu,
            platform.techSpecs.memory,
            platform.techSpecs.display,
            platform.techSpecs.sound,
            existingPlatform?.createdDate,
            existingPlatform?.lastModifiedDate,
            existingPlatform?.version
        )
    }

    fun getAllPlatforms(): Flow<Platform> =
        platformsRepository.findAll()

    suspend fun getPlatformByUrn(platformUrn: URN): Platform? =
        platformsRepository.findFirstByPlatformUrn(platformUrn)

    suspend fun getPlatformByName(name: String): Platform? =
        platformsRepository.findFirstByName(name)
}
