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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;

public final class Platforms {

    public static final PlatformRequest NEO_GEO_AES = PlatformRequestBuilder.builder()
            .name("Neo Geo AES")
            .generation(4)
            .manufacturer("SNK Corporation")
            .type(PlatformType.HOME_VIDEO_GAME_CONSOLE)
            .media(List.of(Media.ROM_CARTRIDGE))
            .year(Year.of(1990))
            .release(ReleaseBuilder.builder()
                    .japan(LocalDate.of(1990, 4, 26))
                    .northAmerica(LocalDate.of(1990, 8, 22))
                    .northAmerica(LocalDate.of(1991, 1, 1))
                    .build())
            .introductoryPrice(BigDecimal.valueOf(649))
            .unitsSold(100_000)
            .discontinuedYear(Year.of(1997))
            .discontinued(true)
            .techSpecs(TechSpecsBuilder.builder()
                    .cpu("Motorola 68000 @ 12MHz, Zilog Z80A @ 4MHz")
                    .memory("64KB RAM, 84KB VRAM, 2KB Sound Memory")
                    .display("320×224 resolution, 4096 on-screen colors out of a palette of 65536")
                    .sound("Yamaha YM2610")
                    .build())
            .build();
}
