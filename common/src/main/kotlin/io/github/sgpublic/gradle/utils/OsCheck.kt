package io.github.sgpublic.gradle.utils

object OsCheck {
    val isWindows: Boolean get() {
        return System.getProperties().getProperty("os.name")
            .toUpperCase().indexOf("WINDOWS") != -1;
    }
}