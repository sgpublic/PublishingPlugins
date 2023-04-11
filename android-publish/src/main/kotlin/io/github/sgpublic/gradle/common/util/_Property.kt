package io.github.sgpublic.gradle.common.util

import org.gradle.api.GradleException
import org.gradle.api.Project

fun Project.findStringProperty(name: String): String? {
    return findProperty(name)?.toString()
}

fun Project.assertStringProperty(name: String): String {
    return findStringProperty(name) ?: throw GradleException("\"$name\" not set, please set it in gradle.propertise")
}