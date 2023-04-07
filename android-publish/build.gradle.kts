import io.github.sgpublic.gradle.assertStringProperty
import io.github.sgpublic.gradle.findStringProperty

plugins {
    kotlin("jvm")

    id("com.gradle.plugin-publish")
}

group = assertStringProperty("publising.project.group")
version = assertStringProperty("publising.project.version")

dependencies {
    implementation(project(":common"))
}

gradlePlugin {
    website.set(findStringProperty("publising.project.website"))
    vcsUrl.set(findStringProperty("publising.project.vcsUrl"))

    plugins {
        create("gradleAndroidPublish") {
            id = "${assertStringProperty("publising.project.group")}.android-publish"
            implementationClass = "io.github.sgpublic.gradle.AndroidPublishingPlugin"
            displayName = "Plugin for Android publishing"
            description = "Use this plugin to distribute Android library with minimal code."
            tags.set(listOf("android-library", "maven-publish"))
        }
    }
}