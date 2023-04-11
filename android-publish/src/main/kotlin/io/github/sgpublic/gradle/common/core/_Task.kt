package io.github.sgpublic.gradle.common.core

import io.github.sgpublic.gradle.common.base.PublishingPlugin
import org.gradle.api.Action
import org.gradle.api.Task
import org.gradle.api.tasks.TaskContainer
import java.util.regex.Pattern
import javax.annotation.RegEx

fun TaskContainer.createIfAbsent(name: String, configurationAction: Action<in Task>) {
    findByName(name)?.let {
        PublishingPlugin.LOGGER.info("task '$name' already exist.")
        return
    }
    register(name, configurationAction)
}
fun TaskContainer.filter(@RegEx regex: String): List<String> {
    val pattern = Pattern.compile(regex)
    PublishingPlugin.LOGGER.warn(names.toString())
    return names.filter { pattern.matcher(it).matches() }
        .also { PublishingPlugin.LOGGER.warn(it.toString()) }
}