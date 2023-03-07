plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.15.0")
    implementation("io.spring.gradle:dependency-management-plugin:1.1.0")
    implementation("me.qoomon:gradle-git-versioning-plugin:6.4.2")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.0.4")
    implementation("com.github.ben-manes:gradle-versions-plugin:0.45.0")
    implementation("io.micronaut.gradle:micronaut-gradle-plugin:3.7.4")
    implementation("io.micronaut.gradle:micronaut-test-resources-plugin:3.7.4")
    implementation("org.graalvm.buildtools.native:org.graalvm.buildtools.native.gradle.plugin:0.9.20")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
    implementation("org.jetbrains.kotlin:kotlin-allopen:1.8.10")
    implementation("com.github.johnrengelman:shadow:8.1.0")
}
