package io.github.sgpublic.gradle.core

import org.gradle.api.Project

fun Project.applyInfo() {
    version = findProperty("publising.version")
        ?: throw IllegalStateException("Unkonwn XXPreference version!")
}