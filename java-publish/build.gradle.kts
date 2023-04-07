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
        }
    }
}