import io.github.sgpublic.gradle.gradlePluginPublish

plugins {
    kotlin("jvm")

    id("com.gradle.plugin-publish")
}

dependencies {
    implementation(project(":common"))
}

gradlePluginPublish("java-publish") {
    implementationClass = "io.github.sgpublic.gradle.JavaPublishingPlugin"
    displayName = "Plugin for Java publishing"
    description = "Use this plugin to distribute Java library with minimal code."
    tags.set(listOf("java-library", "maven-publish"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}