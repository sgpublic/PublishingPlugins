package io.github.sgpublic.publishingplugin.util

import org.gradle.api.GradleException
import org.gradle.api.Project

fun Project.findStringProperty(name: String): String? {
    return System.getenv(name.replace(".", "_"))
        ?.takeIf { it.isNotBlank() }
        ?: findProperty(name)?.toString()
}

fun Project.assertStringProperty(name: String): String {
    return findStringProperty(name)
        ?: throw GradleException("\"$name\" not set, please set it in gradle.properties or environment.")
}