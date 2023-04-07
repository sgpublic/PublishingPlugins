package io.github.sgpublic.gradle.base

import io.github.sgpublic.gradle.core.applyInfo
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.Logger

abstract class PublishingPlugin: Plugin<Project> {
    final override fun apply(target: Project) {
        ROOT_PROJECT = target.rootProject
        LOGGER = target.logger
        target.applyInfo()
        configPublishing(target)
    }

    abstract fun configPublishing(target: Project)

    companion object {
        lateinit var ROOT_PROJECT: Project private set
        lateinit var LOGGER: Logger private set

        fun findProperty(name: String): String? {
            return ROOT_PROJECT.findProperty(name)?.toString()
        }

        fun assertProperty(name: String): String {
            return findProperty(name) ?: throw GradleException("\"$name\" not set, please set it in gradle.propertise")
        }
    }
}