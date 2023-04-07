package io.github.sgpublic.gradle

import io.github.sgpublic.gradle.base.PublishingPlugin
import io.github.sgpublic.gradle.core.applyJavaPublishing
import org.gradle.api.Project

class JavaPublishingPlugin : PublishingPlugin() {
    override fun configPublishing(target: Project) {
        target.applyJavaPublishing()
    }
}