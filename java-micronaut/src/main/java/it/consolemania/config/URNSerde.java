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
package it.consolemania.config;

import com.jcabi.urn.URN;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Encoder;
import io.micronaut.serde.Serde;
import jakarta.inject.Singleton;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

@Singleton
public class URNSerde implements Serde<URN> {
    @Override
    public URN deserialize(
            @NotNull Decoder decoder, @NotNull DecoderContext context, @NotNull Argument<? super URN> type)
            throws IOException {
        var urn = decoder.decodeString();
        return URN.create(urn);
    }

    @Override
    public void serialize(
            @NotNull Encoder encoder,
            @NotNull EncoderContext context,
            @NotNull Argument<? extends URN> type,
            @NotNull URN value)
            throws IOException {
        encoder.encodeString(value.toString());
    }
}
