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
        create("gradle-java-publish") {
            id = "io.github.sgpublic.gradle-java-publish"
            implementationClass = "io.github.sgpublic.gradle.JavaPublishingPlugin"
        }
    }
}