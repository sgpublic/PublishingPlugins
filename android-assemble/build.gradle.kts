plugins {
    kotlin("jvm")
    `kotlin-dsl`

    id("maven-publish")
    id("signing")
}

dependencies {
    /* https://mvnrepository.com/artifact/net.dongliu/apk-parser */
    implementation("net.dongliu:apk-parser:2.6.10")
}

gradlePlugin {
    plugins {
        create("gradle-android-assemble") {
            id = "io.github.sgpublic.gradle-android-assemble"
            implementationClass = "io.github.sgpublic.gradle.AndroidAssemblePlugin"
        }
    }
}