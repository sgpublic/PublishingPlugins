package io.github.sgpublic.publishingplugin

import io.github.sgpublic.publishingplugin.base.PublishingPlugin
import io.github.sgpublic.publishingplugin.core.applyJavaPublishing
import org.gradle.api.Project

class JavaPublishingPlugin : PublishingPlugin() {
    override fun configPublishing(target: Project) {
        target.applyJavaPublishing()
    }
}