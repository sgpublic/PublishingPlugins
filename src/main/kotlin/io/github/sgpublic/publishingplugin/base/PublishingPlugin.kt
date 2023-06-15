package io.github.sgpublic.publishingplugin.base

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.Logger

abstract class PublishingPlugin: Plugin<Project> {
    final override fun apply(target: Project) {
        ROOT_PROJECT = target.rootProject
        LOGGER = target.logger
        configPublishing(target)
    }

    abstract fun configPublishing(target: Project)

    companion object {
        lateinit var ROOT_PROJECT: Project private set
        lateinit var LOGGER: Logger private set
    }
}