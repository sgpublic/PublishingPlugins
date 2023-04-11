package io.github.sgpublic.gradle

import io.github.sgpublic.gradle.common.base.PublishingPlugin
import io.github.sgpublic.gradle.common.core.applyJavaPublishing
import org.gradle.api.Project

class JavaPublishingPlugin : PublishingPlugin() {
    override fun configPublishing(target: Project) {
        target.applyJavaPublishing()
    }
}