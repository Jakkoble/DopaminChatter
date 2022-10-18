import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
}

group = "de.jakkoble"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.twitch4j:twitch4j:1.12.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation(group = "ch.qos.logback", name = "logback-classic", version = "1.2.10")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}