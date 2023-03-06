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
package it.consolemania

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.jcabi.urn.URN
import it.consolemania.games.Games
import it.consolemania.platforms.Platforms
import org.springframework.context.support.beans
import java.io.IOException

object ApplicationConfig {
    val common = listOf(
        commonBeans
    )

    val catalog = listOf(
        Games.beans,
        Platforms.beans
    )
}

val commonBeans = beans {
    bean<ObjectMapper>() {
        ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .registerModule(customSerializerModule())
            .registerModule(JavaTimeModule())
            .registerModule(KotlinModule.Builder().build())
    }
}

fun customSerializerModule(): Module? {
    val module = SimpleModule()
    module.addSerializer(URN::class.java, URNSerializer())
    return module
}

internal class URNSerializer @JvmOverloads constructor(t: Class<URN?>? = null) :
    StdSerializer<URN>(t) {
    @Throws(IOException::class)
    override fun serialize(value: URN, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeString(value.toString())
    }
}
