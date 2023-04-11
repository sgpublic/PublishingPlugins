import io.github.sgpublic.gradle.assertStringProperty
import io.github.sgpublic.gradle.gradlePluginPublish

plugins {
    kotlin("jvm")

    id("com.gradle.plugin-publish")
}

dependencies {
    /* https://mvnrepository.com/artifact/net.dongliu/apk-parser */
    implementation("net.dongliu:apk-parser:2.6.10")
}

gradlePluginPublish("android-assemble") {
    implementationClass = "io.github.sgpublic.gradle.AndroidAssemblePlugin"
    displayName = "Plugin for apk and aar packaging"
    description = "A plugin that provides extension capabilities for apk and aar packaging"
    tags.set(listOf("android"))
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