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
package it.consolemania.catalog.config;

import com.jcabi.urn.URN;
import it.consolemania.catalog.games.GamesRepository;
import it.consolemania.catalog.platforms.PlatformsRepository;
import jakarta.annotation.Nonnull;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJdbcRepositories(basePackageClasses = {GamesRepository.class, PlatformsRepository.class})
@EnableJdbcAuditing
@EnableTransactionManagement
public class DataConfig extends AbstractJdbcConfiguration {

    @Override
    protected List<?> userConverters() {
        return List.of(new URNReadingConverter(), new URNWritingConverter());
    }

    @WritingConverter
    static class URNWritingConverter implements Converter<URN, String> {
        @Override
        public String convert(URN value) {
            return value.toString();
        }
    }

    @ReadingConverter
    static class URNReadingConverter implements Converter<String, URN> {
        @Override
        public URN convert(@Nonnull String value) {
            return URN.create(value);
        }
    }
}
