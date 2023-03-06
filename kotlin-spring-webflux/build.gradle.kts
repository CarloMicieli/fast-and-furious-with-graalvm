import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("kotlin-common-conventions")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-reactor-netty")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("org.springframework.hateoas:spring-hateoas")

    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    runtimeOnly("org.postgresql:r2dbc-postgresql:1.0.0.RELEASE")
    runtimeOnly("org.postgresql:postgresql")
}

tasks.getByName<BootRun>("bootRun") {
    mainClass.set("it.consolemania.ApplicationKt")
}

tasks.named<BootBuildImage>("bootBuildImage") {
    builder.set("paketobuildpacks/builder:tiny")
    imageName.set("ghcr.io/carlomicieli/consolemania-spring-webflux:${project.version}")
    tags.set(listOf("ghcr.io/carlomicieli/consolemania-spring-webflux:latest"))
}

springBoot {
    buildInfo()
}
