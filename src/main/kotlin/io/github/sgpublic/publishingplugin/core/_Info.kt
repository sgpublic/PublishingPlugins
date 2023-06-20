package io.github.sgpublic.publishingplugin.core

import io.github.sgpublic.publishingplugin.util.assertStringProperty
import io.github.sgpublic.publishingplugin.util.findStringProperty
import org.gradle.api.Project

val Project.libVersion: String get() {
    return findStringProperty("publishing.project.${name}.version")
        ?: assertStringProperty("publishing.project.version")
}