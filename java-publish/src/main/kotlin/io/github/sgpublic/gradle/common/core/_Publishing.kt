package io.github.sgpublic.gradle.common.core

import io.github.sgpublic.gradle.common.util.assertStringProperty
import io.github.sgpublic.gradle.common.util.findStringProperty
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.configurationcache.extensions.capitalized
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

    val rootName = project.findStringProperty("publising.project.name")
        ?: project.rootProject.name
    val taskName = rootName + project.name.split("-")
        .map { it.capitalized() }
        .joinToString("")
    val projectName = project.findStringProperty("publising.project.${project.name}.name")
        ?: (rootName.toLowerCase() + "-" + project.name)

    project.extensions.configure<PublishingExtension>("publishing") {
        publications {
            register(taskName, MavenPublication::class.java) {
                groupId = project.assertStringProperty("publising.project.group")
                version = project.libVersion
                artifactId = projectName

                pom {
                    name.set(projectName)
                    description.set(projectName)
                    project.findStringProperty("publising.project.url")?.let {
                        url.set(it)
                    }

                    project.findStringProperty("publising.license.name")?.let {
                        licenses {
                            license {
                                name.set(it)
                                project.findStringProperty("publising.license.url")?.let {
                                    url.set(it)
                                }
                            }
                        }
                    }

                    project.findStringProperty("publising.issue.system")?.let {
                        issueManagement {
                            system.set(it)
                            url.set(project.assertStringProperty("publising.issue.url"))
                        }
                    }
                    scm {
                        connection.set(project.assertStringProperty("publising.smc.connection"))
                        developerConnection.set(project.assertStringProperty("publising.smc.developerConnection"))
                        url.set(project.assertStringProperty("publising.smc.url"))
                    }
                    project.findStringProperty("publising.developer.id")?.let {
                        developers {
                            developer {
                                id.set(it)
                                name.set(project.assertStringProperty("publising.developer.name"))
                                email.set(project.assertStringProperty("publising.developer.email"))
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