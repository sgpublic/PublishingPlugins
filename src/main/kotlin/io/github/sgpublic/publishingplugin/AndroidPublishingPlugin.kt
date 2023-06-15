package io.github.sgpublic.publishingplugin

import io.github.sgpublic.publishingplugin.base.PublishingPlugin
import io.github.sgpublic.publishingplugin.core.applyAndroidPublishing
import org.gradle.api.Project

class AndroidPublishingPlugin : PublishingPlugin() {
    override fun configPublishing(target: Project) {
        target.applyAndroidPublishing()
    }
}