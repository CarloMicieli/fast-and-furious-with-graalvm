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
package it.consolemania.catalog.platforms;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcabi.urn.URN;
import it.consolemania.catalog.games.GamesService;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("PlatformsController")
@WebMvcTest(PlatformsController.class)
class PlatformsControllerTest {

    private static final URN FIXED_URN = PlatformURN.of("Neo Geo AES");

    @MockBean
    private PlatformsService platformsService;

    @MockBean
    private GamesService gamesService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("it should create new platforms")
    void shouldPostNewPlatforms() throws Exception {
        var request = Platforms.NEO_GEO_AES;

        when(platformsService.createPlatform(request)).thenReturn(FIXED_URN);

        mockMvc.perform(post("/platforms")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/platforms/" + FIXED_URN));
    }

    @Test
    @DisplayName("it should return a BAD_REQUEST when the new platform request is not valid")
    void shouldValidateNewPlatformRequests() throws Exception {
        var request = Platforms.NEO_GEO_AES.withName("");
        mockMvc.perform(post("/platforms")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("it should update platforms")
    void shouldUpdatePlatforms() throws Exception {
        var request = Platforms.NEO_GEO_AES;

        mockMvc.perform(put("/platforms/{platformUrn}", FIXED_URN)
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(platformsService).updatePlatform(FIXED_URN, request);
    }

    @Test
    @DisplayName("it should return a BAD_REQUEST when the updated platform request is not valid")
    void shouldValidatePlatformUpdateRequests() throws Exception {
        var request = Platforms.NEO_GEO_AES.withName("");
        mockMvc.perform(put("/platforms/{platformUrn}", FIXED_URN)
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("it should get all platforms")
    void shouldGetAllPlatforms() throws Exception {
        mockMvc.perform(get("/platforms").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @DisplayName("it should return 404 when there is no platform with the given id")
    void shouldReturn404ForNotFoundPlatform() throws Exception {
        mockMvc.perform(get("/platforms/{platformUrn}", FIXED_URN).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("it should get all games by platform urn")
    void shouldGetAllGamesByPlatformId() throws Exception {
        var id = UUID.randomUUID();
        var platform = mock(Platform.class);
        when(platform.platformId()).thenReturn(id);

        when(platformsService.getPlatformByUrn(FIXED_URN)).thenReturn(Optional.of(platform));
        mockMvc.perform(get("/platforms/{id}/games", FIXED_URN).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(gamesService).getGamesByPlatform(id);
    }

    String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
