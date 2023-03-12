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
import io.micronaut.core.convert.ConversionContext;
import io.micronaut.data.model.runtime.convert.AttributeConverter;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.NotNull;

@Singleton
public class URNAttributeConverter implements AttributeConverter<URN, String> {
    @Override
    public String convertToPersistedValue(URN entityValue, @NotNull ConversionContext context) {
        return entityValue == null ? null : entityValue.toString();
    }

    @Override
    public URN convertToEntityValue(String persistedValue, @NotNull ConversionContext context) {
        return URN.create(persistedValue);
    }
}
