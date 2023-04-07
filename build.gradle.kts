plugins {
    val kotlinVer = "1.5.31"
    kotlin("jvm") version kotlinVer apply false

    `kotlin-dsl` apply false

    id("com.gradle.plugin-publish") version "1.1.0" apply false
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}