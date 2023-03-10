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
package it.consolemania.platforms;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.problem.HttpStatusType;
import jakarta.inject.Singleton;
import java.net.URI;
import javax.validation.ConstraintViolationException;
import org.zalando.problem.Problem;
import org.zalando.problem.ThrowableProblem;

@Produces
@Singleton
@Requires(classes = {ConstraintViolationException.class, ExceptionHandler.class})
public class PlatformsExceptionHandler implements ExceptionHandler<ConstraintViolationException, ThrowableProblem> {
    @Override
    public ThrowableProblem handle(HttpRequest request, ConstraintViolationException exception) {
        return Problem.builder()
                .withType(URI.create("https://example.org/out-of-stock"))
                .withTitle("Out of Stock")
                .withStatus(new HttpStatusType(HttpStatus.BAD_REQUEST))
                .withDetail("Item B00027Y5QG is no longer available")
                .with("product", "B00027Y5QG")
                .build();
    }
}
