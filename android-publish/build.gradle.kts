plugins {
    kotlin("jvm")
    `kotlin-dsl`

    id("maven-publish")
    id("signing")
}

dependencies {
    implementation(project(":common"))
}

gradlePlugin {
    plugins {
        create("gradle-android-publish") {
            id = "io.github.sgpublic.gradle-android-publish"
            implementationClass = "io.github.sgpublic.gradle.AndroidPublishingPlugin"
        }
    }
}