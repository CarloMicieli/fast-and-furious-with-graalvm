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
package it.consolemania.games;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.jcabi.urn.URN;
import it.consolemania.ResourceMetadata;
import java.time.Year;
import java.util.List;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.RepresentationModel;

public final class GameModel extends RepresentationModel<GameModel> {

    private final URN gameUrn;
    private final String title;
    private final List<Genre> genres;
    private final List<Mode> modes;
    private final String series;
    private final String developer;
    private final String publisher;
    private final Rating rating;
    private final String plot;
    private final Year year;
    private final ResourceMetadata metadata;

    public GameModel(
            URN gameUrn,
            String title,
            List<Genre> genres,
            List<Mode> modes,
            String series,
            String developer,
            String publisher,
            String plot,
            Rating rating,
            Year year,
            ResourceMetadata metadata) {
        super(linkTo(GamesController.class).slash(gameUrn).withRel(IanaLinkRelations.SELF));

        this.gameUrn = gameUrn;
        this.title = title;
        this.genres = genres;
        this.modes = modes;
        this.series = series;
        this.developer = developer;
        this.publisher = publisher;
        this.rating = rating;
        this.plot = plot;
        this.year = year;
        this.metadata = metadata;
    }

    public URN getGameUrn() {
        return gameUrn;
    }

    public String getTitle() {
        return title;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<Mode> getModes() {
        return modes;
    }

    public String getSeries() {
        return series;
    }

    public String getDeveloper() {
        return developer;
    }

    public String getPublisher() {
        return publisher;
    }

    public Rating getRating() {
        return rating;
    }

    public String getPlot() {
        return plot;
    }

    public Year getYear() {
        return year;
    }

    public ResourceMetadata getMetadata() {
        return metadata;
    }

    public static GameModel of(Game game) {
        var metadata = new ResourceMetadata(game.createdDate(), game.lastModifiedDate(), game.version());
        return new GameModel(
                game.gameUrn(),
                game.title(),
                game.genres(),
                game.modes(),
                game.series(),
                game.developer(),
                game.publisher(),
                game.plot(),
                game.rating(),
                Year.of(game.year()),
                metadata);
    }
}
