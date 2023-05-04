package io.github.sgpublic.gradle

import org.gradle.api.Project
import org.gradle.plugin.devel.GradlePluginDevelopmentExtension
import org.gradle.plugin.devel.PluginDeclaration

fun Project.applyProjectInfo() {
    group = assertStringProperty("publising.project.group")
    version = assertStringProperty("publising.project.version")
}

fun Project.gradlePluginPublish(id: String, call: PluginDeclaration.() -> Unit) {
    applyProjectInfo()

    project.extensions.configure<GradlePluginDevelopmentExtension>("gradlePlugin") {
        website.set(findStringProperty("publising.project.website"))
        vcsUrl.set(findStringProperty("publising.project.vcsUrl"))

        plugins {
            create("gradle${id.split("-").joinToString { it.capitalize() }}") {
                this.id = "${assertStringProperty("publising.project.group")}.$id"
                call.invoke(this)
            }
        }
    }
}