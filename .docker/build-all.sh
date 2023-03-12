#!/bin/bash
./gradlew java-micronaut-jdbc:dockerBuildNative
./gradlew java-micronaut-r2dbc:dockerBuild
./gradlew kotlin-spring-webflux:bootBuildImage
./gradlew java-spring-mvc:bootBuildImage
