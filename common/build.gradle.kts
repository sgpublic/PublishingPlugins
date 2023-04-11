import io.github.sgpublic.gradle.applyProjectInfo

plugins {
    kotlin("jvm")
    `kotlin-dsl`
}

applyProjectInfo()

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}