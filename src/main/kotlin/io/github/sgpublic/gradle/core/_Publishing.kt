package io.github.sgpublic.gradle.core

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import io.github.sgpublic.gradle.base.PublishingPlugin
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.internal.impldep.com.google.gson.internal.bind.TypeAdapters.URI
import org.gradle.plugins.signing.SigningExtension

fun Project.applyJavaPublishing() {
    applyPublishing(this, "java")
}

fun Project.applyAndroidPublishing() {
    applyPublishing(this, "release")
}

private fun applyPublishing(project: Project, type: String) {
    val publishing_username = (project.findProperty("publising.username") ?: return).toString()
    val publishing_password = (project.findProperty("publising.password") ?: return).toString()
    project.properties.keys.filter { it.startsWith("signing.") }.let {
        if (it.isEmpty()) return
    }

    val rootName = PublishingPlugin.findProperty("publising.name")
        ?: PublishingPlugin.ROOT_PROJECT.name
    val taskName = rootName + project.name.split("-")
        .map { it.capitalized() }
        .joinToString("")
    val projectName = rootName.toLowerCase() + "-" + project.name

    project.extensions.configure<PublishingExtension>("publishing") {
        publications {
            register(taskName, MavenPublication::class.java) {
                groupId = project.group.toString()
                artifactId = projectName
                version = project.version.toString()

                pom {
                    name.set(projectName)
                    description.set(projectName)
                    PublishingPlugin.findProperty("publising.url")?.let {
                        url.set(it)
                    }

                    PublishingPlugin.findProperty("publising.license.name")?.let {
                        licenses {
                            license {
                                name.set(it)
                                PublishingPlugin.findProperty("publising.license.url")?.let {
                                    url.set(it)
                                }
                            }
                        }
                    }

                    PublishingPlugin.findProperty("publising.issue.system")?.let {
                        issueManagement {
                            system.set(it)
                            url.set(PublishingPlugin.assertProperty("publising.issue.url"))
                        }
                    }
                    scm {
                        connection.set(PublishingPlugin.assertProperty("publising.smc.connection"))
                        developerConnection.set(PublishingPlugin.assertProperty("publising.smc.developerConnection"))
                        url.set(PublishingPlugin.assertProperty("publising.smc.url"))
                    }
                    PublishingPlugin.findProperty("publising.developer.id")?.let {
                        developers {
                            developer {
                                id.set(it)
                                name.set(PublishingPlugin.assertProperty("publising.developer.name"))
                                email.set(PublishingPlugin.assertProperty("publising.developer.email"))
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
            maven {
                name = "ossrh"
                url = if (project.version.toString().endsWith("-SNAPSHOT")) {
                    java.net.URI.create("https://oss.sonatype.org/content/repositories/snapshots")
                } else {
                    java.net.URI.create("https://oss.sonatype.org/service/local/staging/deploy/maven2")
                }
                credentials {
                    username = publishing_username
                    password = publishing_password
                }
            }
        }
    }

    project.extensions.configure<SigningExtension>("signing") {
        val publishing = project.extensions.getByName("publishing") as PublishingExtension
        sign(publishing.publications.getByName(taskName))
    }
}