package io.github.sgpublic.publishingplugin.core

import io.github.sgpublic.publishingplugin.util.assertStringProperty
import io.github.sgpublic.publishingplugin.util.findStringProperty
import org.gradle.api.Project

val Project.libVersion: String get() {
    return findStringProperty("publising.project.${name}.version")
        ?: assertStringProperty("publising.project.version")
}