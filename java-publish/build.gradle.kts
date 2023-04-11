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
        create("gradleJavaPublish") {
            id = "${assertStringProperty("publising.project.group")}.java-publish"
            implementationClass = "io.github.sgpublic.gradle.JavaPublishingPlugin"
            displayName = "Plugin for Java publishing"
            description = "Use this plugin to distribute Java library with minimal code."
            tags.set(listOf("java-library", "maven-publish"))
        }
    }
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