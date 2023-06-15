package io.github.sgpublic.gradle.common.core

import io.github.sgpublic.gradle.common.util.assertStringProperty
import io.github.sgpublic.gradle.common.util.findStringProperty
import org.gradle.api.Project

val Project.libVersion: String get() {
    return findStringProperty("publising.project.${name}.version")
        ?: assertStringProperty("publising.project.version")
}