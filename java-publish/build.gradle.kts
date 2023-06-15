import io.github.sgpublic.gradle.findStringProperty
import io.github.sgpublic.gradle.gradlePluginPublish

plugins {
    kotlin("jvm")
    `kotlin-dsl`

    `java-gradle-plugin`
    id("com.gradle.plugin-publish")
}

findStringProperty("publising.gitlab.host")?.let {  gitlabHost ->
    publishing {
        repositories {
            maven {
                setUrl("https://${gitlabHost}" +
                        "/api/v4/projects/${findStringProperty("publising.gitlab.projectId")}" +
                        "/packages/maven")
                credentials(HttpHeaderCredentials::class.java) {
                    name = "Private-Token"
                    value = findStringProperty("publising.gitlab.token")
                }
                authentication {
                    create("header", HttpHeaderAuthentication::class)
                }
            }
        }
    }
}


gradlePluginPublish("java-publish") {
    implementationClass = "io.github.sgpublic.gradle.JavaPublishingPlugin"
    displayName = "Plugin for Java publishing"
    description = "Use this plugin to distribute Java library with minimal code."
    tags.set(listOf("java-library", "maven-publish"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}