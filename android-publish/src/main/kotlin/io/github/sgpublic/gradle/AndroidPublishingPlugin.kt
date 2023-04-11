package io.github.sgpublic.gradle

import io.github.sgpublic.gradle.common.base.PublishingPlugin
import io.github.sgpublic.gradle.common.core.applyAndroidPublishing
import org.gradle.api.Project

class AndroidPublishingPlugin : PublishingPlugin() {
    override fun configPublishing(target: Project) {
        target.applyAndroidPublishing()
    }
}