package io.github.sgpublic.publishingplugin.core

import io.github.sgpublic.publishingplugin.util.assertStringProperty
import io.github.sgpublic.publishingplugin.util.findStringProperty
import org.gradle.api.Project
import org.gradle.api.credentials.HttpHeaderCredentials
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.authentication.http.HttpHeaderAuthentication
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.create
import org.gradle.plugins.signing.SigningExtension
import java.net.URI

fun Project.applyJavaPublishing() {
    applyPublishing(this, "java")
}

fun Project.applyAndroidPublishing() {
    applyPublishing(this, "release")
}

private fun applyPublishing(project: Project, type: String) {
    project.properties.keys.filter { it.startsWith("signing.") }.let {
        if (it.isEmpty()) return
    }

    val rootName = project.findStringProperty("publishing.project.name")
        ?: project.rootProject.name
    val taskName = rootName + project.name
        .split("-")
        .joinToString("") {
            it.capitalized()
        }
    val projectName = project.findStringProperty("publishing.project.${project.name}.name")
        ?: (rootName.toLowerCase() + "-" + project.name)

    project.extensions.configure<PublishingExtension>("publishing") {
        publications {
            register(taskName, MavenPublication::class.java) {
                groupId = project.assertStringProperty("publishing.project.group")
                version = project.libVersion
                artifactId = projectName

                pom {
                    name.set(projectName)
                    description.set(projectName)
                    project.findStringProperty("publishing.project.url")?.let {
                        url.set(it)
                    }

                    project.findStringProperty("publishing.license.name")?.let {
                        licenses {
                            license {
                                name.set(it)
                                project.findStringProperty("publishing.license.url")?.let {
                                    url.set(it)
                                }
                            }
                        }
                    }

                    project.findStringProperty("publishing.issue.system")?.let {
                        issueManagement {
                            system.set(it)
                            url.set(project.assertStringProperty("publishing.issue.url"))
                        }
                    }
                    scm {
                        connection.set(project.assertStringProperty("publishing.smc.connection"))
                        developerConnection.set(project.assertStringProperty("publishing.smc.developerConnection"))
                        url.set(project.assertStringProperty("publishing.smc.url"))
                    }
                    project.findStringProperty("publishing.developer.id")?.let {
                        developers {
                            developer {
                                id.set(it)
                                name.set(project.assertStringProperty("publishing.developer.name"))
                                email.set(project.assertStringProperty("publishing.developer.email"))
                            }
                        }
                    }
                }
                project.afterEvaluate {
                    from(project.components.getByName(type))
                }
            }
        }
        repositories {
            val publishing_username = project.findStringProperty("publishing.ossrh.username")
            val publishing_password = project.findStringProperty("publishing.ossrh.password")
            if (publishing_username != null && publishing_password != null) {
                maven {
                    name = "ossrh"
                    url = if (project.version.toString().endsWith("-SNAPSHOT")) {
                        URI.create("https://oss.sonatype.org/content/repositories/snapshots")
                    } else {
                        URI.create("https://oss.sonatype.org/service/local/staging/deploy/maven2")
                    }
                    credentials {
                        username = publishing_username
                        password = publishing_password
                    }
                }
            }

            val gitlabHost = project.findStringProperty("publishing.gitlab.host")
            val gitlabProjectId = project.findStringProperty("publishing.gitlab.projectId")
            if (gitlabHost != null && gitlabProjectId != null) {
                maven {
                    name = "gitlab"
                    url = URI.create("https://$gitlabHost/api/v4/projects/$gitlabProjectId/packages/maven")
                    credentials(HttpHeaderCredentials::class.java) {
                        name = "Private-Token"
                        value = project.assertStringProperty("publishing.gitlab.token")
                    }
                    authentication {
                        create("header", HttpHeaderAuthentication::class)
                    }
                }
            }
        }
    }

    project.extensions.configure<SigningExtension>("signing") {
        val publishing = project.extensions.getByName("publishing") as PublishingExtension
        sign(publishing.publications.getByName(taskName))
    }
}