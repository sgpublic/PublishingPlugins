plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/net.dongliu/apk-parser
    implementation("net.dongliu:apk-parser:2.6.10")
}

sourceSets {
    getByName("main") {
        java {
            srcDirs.add(
                rootProject.file("buildSrcExt/src")
            )
        }
        resources {
            srcDirs.add(
                rootProject.file("buildSrcExt/res")
            )
        }
    }
}