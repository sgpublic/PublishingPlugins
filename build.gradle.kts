plugins {
    val kotlinVer = "1.5.31"
    kotlin("jvm") version kotlinVer apply false

    `kotlin-dsl` apply false
}

group = "io.github.sgpublic"
version = "0.1.0"

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}