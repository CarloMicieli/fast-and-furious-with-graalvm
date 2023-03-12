import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage

plugins {
    id("java-common-conventions")
    id("com.github.johnrengelman.shadow")
    id("io.micronaut.application")
}

dependencies {
    implementation(project(":shared-java-library"))
    implementation("org.jetbrains:annotations:23.0.0")
    annotationProcessor("io.micronaut:micronaut-graal")
    annotationProcessor("io.micronaut.data:micronaut-data-processor")
    annotationProcessor("io.micronaut:micronaut-http-validation")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    annotationProcessor("io.micronaut:micronaut-validation")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-management")
    implementation("io.micronaut.data:micronaut-data-jdbc")
    implementation("io.micronaut.flyway:micronaut-flyway")
    implementation("io.micronaut.problem:micronaut-problem-json")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut:micronaut-validation")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("jakarta.persistence:jakarta.persistence-api:2.2.3")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("org.slf4j:log4j-over-slf4j")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("io.micronaut.test:micronaut-test-rest-assured")
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:testcontainers")
    compileOnly("org.graalvm.nativeimage:svm")
}

application {
    mainClass.set("it.consolemania.Application")
}

graalvmNative.toolchainDetection.set(false)
micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("it.consolemania.*")
    }
}

configurations.all {
    resolutionStrategy.dependencySubstitution {
        substitute(module("io.micronaut:micronaut-jackson-databind"))
            .using(module("io.micronaut.serde:micronaut-serde-jackson:1.5.2"))
    }
}

tasks.named<DockerBuildImage>("dockerBuild") {
    images.add("ghcr.io/carlomicieli/consolemania-micronaut-jdbc:${project.version}")
    images.add("ghcr.io/carlomicieli/consolemania-micronaut-jdbc:latest")
}

tasks.named<DockerBuildImage>("dockerBuildNative") {
    images.add("ghcr.io/carlomicieli/consolemania-micronaut-jdbc-native:${project.version}")
    images.add("ghcr.io/carlomicieli/consolemania-micronaut-jdbc-native:latest")
}
