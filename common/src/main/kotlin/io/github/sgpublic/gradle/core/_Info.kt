package io.github.sgpublic.gradle.core

import io.github.sgpublic.gradle.util.assertStringProperty
import org.gradle.api.Project

fun Project.applyInfo() {
    group = assertStringProperty("publising.project.group")
    version = assertStringProperty("publising.project.version")
}