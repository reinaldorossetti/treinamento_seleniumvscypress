import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin("jvm") version "1.6.0"
    id("io.qameta.allure") version "2.8.1"
    id("org.gradle.test-retry") version "1.3.1"
    java
}

group "com.seleniumvscypress"
version "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("org.hamcrest:hamcrest:2.2")
    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("org.junit.platform:junit-platform-launcher:1.8.2")

    implementation("org.jetbrains.kotlin:kotlin-test:1.6.0")
    testImplementation("io.rest-assured:rest-assured:4.4.0")
    testImplementation("io.rest-assured:kotlin-extensions:4.4.0")
    testImplementation("io.rest-assured:json-schema-validator:4.4.0")
    testImplementation("com.github.javafaker:javafaker:1.0.2")
    testImplementation("io.qameta.allure:allure-junit5:2.17.1")
    implementation("io.qameta.allure:allure-okhttp3:2.16.1")

    testImplementation("io.qameta.allure:allure-kotlin-model:2.2.7")
    testImplementation("io.qameta.allure:allure-kotlin-commons:2.2.7")
    testImplementation("io.qameta.allure:allure-kotlin-junit4:2.2.7")

    // frameworks de teste.
    // https://mvnrepository.com/artifact/io.appium/java-client
    implementation("io.appium:java-client:8.0.0-beta")
    // https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java
    implementation("org.seleniumhq.selenium:selenium-java:4.0.0")
    // https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-chrome-driver
    implementation("org.seleniumhq.selenium:selenium-chrome-driver:4.0.0")
    // https://mvnrepository.com/artifact/com.applitools/eyes-selenium-java3
    implementation("com.applitools:eyes-selenium-java3:3.210.6")
}

allure {
    version = "2.17.1"
    downloadLinkFormat = "https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/2.17.1/allure-commandline-2.17.1.zip"
}

tasks.test {
    useJUnitPlatform()
    // Configuration parameters to execute top-level classes in parallel but methods in same thread
    systemProperties["junit.jupiter.execution.parallel.enabled"] = true
    systemProperties["junit.jupiter.execution.parallel.mode.default"] = "concurrent"
    maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
    testLogging {
        events(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
        exceptionFormat = TestExceptionFormat.SHORT
        showCauses = true
        showExceptions = true
        showStackTraces = true
    }
}
