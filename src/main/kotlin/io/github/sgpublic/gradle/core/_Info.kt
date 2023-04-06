package io.github.sgpublic.gradle.core

import io.github.sgpublic.gradle.base.PublishingPlugin
import org.gradle.api.Project

fun Project.applyInfo() {
    group = PublishingPlugin.assertProperty("publising.project.group")
    version = PublishingPlugin.assertProperty("publising.project.version")
}