package io.github.sgpublic.gradle.common.core

import io.github.sgpublic.gradle.common.util.assertStringProperty
import org.gradle.api.Project

fun Project.applyInfo() {
    group = assertStringProperty("publising.project.group")
    version = assertStringProperty("publising.project.version")
}