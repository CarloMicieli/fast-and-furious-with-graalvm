import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED

plugins {
    id("java")
    id("me.qoomon.git-versioning")
    id("com.diffplug.spotless")
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

repositories {
    mavenCentral()
}

extra["recordBuilderVersion"] = "35"

dependencies {
    annotationProcessor("io.soabase.record-builder:record-builder-processor:${property("recordBuilderVersion")}")
    compileOnly("io.soabase.record-builder:record-builder-core:${property("recordBuilderVersion")}")
    implementation("com.jcabi:jcabi-urn:0.9")
}

configurations {
    compileClasspath {
        resolutionStrategy.activateDependencyLocking()
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }

    sourceCompatibility = JavaVersion.toVersion("17")
    targetCompatibility = JavaVersion.toVersion("17")
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

spotless {
    java {
        // optional: you can specify import groups directly
        // note: you can use an empty string for all the imports you didn't specify explicitly,
        // '|' to join group without blank line, and '\\#` prefix for static imports
        importOrder("java|javax", "it.consolemania.catalog", "", "\\#it.consolemania.catalog", "\\#")
        removeUnusedImports()

        targetExclude("build/generated/aot*/**")

        palantirJavaFormat("2.9.0")

        formatAnnotations() // fixes formatting of type annotations

        licenseHeaderFile("${project.rootDir}/.spotless/header.txt")

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
