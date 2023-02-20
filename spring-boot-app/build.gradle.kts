import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    java
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.graalvm.buildtools.native") version "0.9.20"
    id("me.qoomon.git-versioning") version "6.4.2"
    id("com.github.ben-manes.versions") version "0.45.0"
    id("com.diffplug.spotless") version "6.15.0"
}

group = "it.consolemania.catalog"
version = "0.0.0-SNAPSHOT"
gitVersioning.apply {
    refs {
        branch("main") {
            version = "\${commit.timestamp}-\${commit.short}"
        }
        tag("v(?<version>.*)") {
            version = "\${ref.version}"
        }
    }

    rev {
        version = "\${commit.short}-SNAPSHOT"
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {
    withType<JavaCompile> {
        options.isIncremental = true
        options.isFork = true
        options.isFailOnError = false

        options.compilerArgs.addAll(
            arrayOf(
                "-Xlint:all",
                "-Xlint:-processing"
            )
        )
    }
}

repositories {
    mavenCentral()
}

configurations {
    all {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    implementation {
        resolutionStrategy {
            failOnVersionConflict()
        }
    }
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

configurations {
    compileClasspath {
        resolutionStrategy.activateDependencyLocking()
    }
}

extra["testcontainersVersion"] = "1.17.6"
extra["recordBuilderVersion"] = "35"

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

dependencies {
    constraints {
        implementation("org.apache.logging.log4j:log4j-core") {
            version {
                strictly("[2.17, 3[")
                prefer("2.17.0")
            }
            because("CVE-2021-44228, CVE-2021-45046, CVE-2021-45105")
        }
    }

    annotationProcessor("io.soabase.record-builder:record-builder-processor:${property("recordBuilderVersion")}")
    compileOnly("io.soabase.record-builder:record-builder-core:${property("recordBuilderVersion")}")
    implementation("com.jcabi:jcabi-urn:0.9")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.flywaydb:flyway-core")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-inline")
}

graalvmNative {
    testSupport.set(false)
}

tasks {
    test {
        useJUnitPlatform()

        minHeapSize = "512m"
        maxHeapSize = "1G"
        failFast = false
        ignoreFailures = true

        testLogging {
            showStandardStreams = false
            events(PASSED, FAILED, SKIPPED)
            showExceptions = true
            showCauses = true
            showStackTraces = true
            exceptionFormat = FULL
        }
    }
}

@Suppress("UnstableApiUsage")
testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
        }

        val integrationTest by registering(JvmTestSuite::class) {
            dependencies {
                implementation(project())
                implementation("org.springframework.boot:spring-boot-starter-test")

                implementation("org.testcontainers:testcontainers")
                implementation("org.testcontainers:junit-jupiter")
                implementation("org.testcontainers:postgresql")
                implementation("io.rest-assured:rest-assured")
            }

            targets {
                all {
                    testTask.configure {
                        shouldRunAfter(test)
                        minHeapSize = "1G"
                        maxHeapSize = "2G"

                        failFast = false

                        testLogging {
                            showStandardStreams = false
                            events(PASSED, FAILED, SKIPPED)
                            showExceptions = true
                            showCauses = true
                            showStackTraces = true
                            exceptionFormat = FULL
                        }
                    }
                }
            }
        }
    }
}

tasks.named("check") {
    dependsOn(testing.suites.named("integrationTest"))
}

tasks.named<BootBuildImage>("bootBuildImage") {
    builder.set("paketobuildpacks/builder:tiny")
    imageName.set("ghcr.io/carlomicieli/spring-boot-consolemania:${project.version}")
    tags.set(listOf("ghcr.io/carlomicieli/spring-boot-consolemania:latest"))
}

tasks.getByName<BootRun>("bootRun") {
    mainClass.set("it.consolemania.catalog.CatalogServiceApplication")
}

spotless {
    java {
        // optional: you can specify import groups directly
        // note: you can use an empty string for all the imports you didn't specify explicitly,
        // '|' to join group without blank line, and '\\#` prefix for static imports
        importOrder("java|javax", "it.consolemania.catalog, "", "\\#it.consolemania.catalog", "\\#")
        removeUnusedImports()

        targetExclude("build/generated/aot*/**")

        palantirJavaFormat("2.9.0")

        formatAnnotations() // fixes formatting of type annotations

        licenseHeaderFile(".spotless/header.txt")

        toggleOffOn("fmt:off", "fmt:on")
        indentWithSpaces()
        trimTrailingWhitespace()
        endWithNewline()
    }

    kotlinGradle {
        endWithNewline()
        ktlint()
        indentWithSpaces()
        trimTrailingWhitespace()
    }
}
