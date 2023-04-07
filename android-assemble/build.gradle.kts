import io.github.sgpublic.gradle.assertStringProperty
import io.github.sgpublic.gradle.findStringProperty

plugins {
    kotlin("jvm")

    id("com.gradle.plugin-publish")
}

group = assertStringProperty("publising.project.group")
version = assertStringProperty("publising.project.version")

dependencies {
    /* https://mvnrepository.com/artifact/net.dongliu/apk-parser */
    implementation("net.dongliu:apk-parser:2.6.10")
}

gradlePlugin {
    website.set(findStringProperty("publising.project.website"))
    vcsUrl.set(findStringProperty("publising.project.vcsUrl"))

    plugins {
        create("gradleAndroidAssemble") {
            id = "${assertStringProperty("publising.project.group")}.android-assemble"
            implementationClass = "io.github.sgpublic.gradle.AndroidAssemblePlugin"
            displayName = "Plugin for apk and aar packaging"
            description = "A plugin that provides extension capabilities for apk and aar packaging"
            tags.set(listOf("android"))
        }
    }
}