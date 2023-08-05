plugins {
    kotlin("jvm") version "1.7.10"
    `kotlin-dsl`

    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "1.2.0"
}

group = "io.github.sgpublic"
version = "1.0.0-alpha01"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    testImplementation(kotlin("test"))
}


tasks.test {
    useJUnitPlatform()
}

publishing {
    repositories {
        mavenLocal()
    }
}

gradlePlugin {
    website.set("https://github.com/sgpublic/PublishingPlugins")
    vcsUrl.set("https://github.com/sgpublic/PublishingPlugins.git")

    plugins {
        create("gradleJavaPublish") {
            this.id = "${group}.java-publish"
            implementationClass = "io.github.sgpublic.publishingplugin.JavaPublishingPlugin"
            displayName = "Plugin for Java publishing"
            description = "Use this plugin to distribute Java library with minimal code."
            tags.set(listOf("java-library", "maven-publish"))
        }
        create("gradleAndroidPublish") {
            this.id = "${group}.android-publish"
            implementationClass = "io.github.sgpublic.publishingplugin.AndroidPublishingPlugin"
            displayName = "Plugin for Android publishing"
            description = "Use this plugin to distribute Android library with minimal code."
            tags.set(listOf("android-library", "maven-publish"))
        }
    }
}