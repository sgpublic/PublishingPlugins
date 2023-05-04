//import io.github.sgpublic.gradle.gradlePluginPublish

plugins {
    kotlin("jvm")
    `kotlin-dsl`

    `java-gradle-plugin`
    id("com.gradle.plugin-publish")
}

//gradlePluginPublish("android-publish") {
//    implementationClass = "io.github.sgpublic.gradle.AndroidPublishingPlugin"
//    displayName = "Plugin for Android publishing"
//    description = "Use this plugin to distribute Android library with minimal code."
//    tags.set(listOf("android-library", "maven-publish"))
//}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}