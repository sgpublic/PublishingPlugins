import io.github.sgpublic.gradle.applyProjectInfo
import io.github.sgpublic.gradle.assertStringProperty
import io.github.sgpublic.gradle.gradlePluginPublish

plugins {
    kotlin("jvm")

    id("com.gradle.plugin-publish")
}

applyProjectInfo()

dependencies {
    implementation(project(":common"))
}

gradlePluginPublish("android-publish") {
    implementationClass = "io.github.sgpublic.gradle.AndroidPublishingPlugin"
    displayName = "Plugin for Android publishing"
    description = "Use this plugin to distribute Android library with minimal code."
    tags.set(listOf("android-library", "maven-publish"))
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