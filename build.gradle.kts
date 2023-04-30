plugins {
    id("java")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "by.thmihnea"
version = "1.0-ALPHA"

repositories {
    mavenCentral()
}

dependencies {
    /* Google Guava - Java Utilities */
    implementation(group = "com.google.guava", name = "guava", version = "31.1-jre")

    /* Google GSON - JSON Serialization API */
    implementation(group = "com.google.code.gson", name = "gson", version = "2.9.1")

    /* JetBrains Annotations - Annotations for Java */
    implementation(group = "org.jetbrains", name = "annotations", version = "16.0.2")

    /* Apache Commons */
    implementation(group = "org.apache.commons", name = "commons-lang3", version = "3.0")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "by.thmihnea.weatherapp.WeatherForecast"
    }
}

tasks.withType<JavaCompile> {
    options.release.set(16)
}