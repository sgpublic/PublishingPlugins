package io.github.sgpublic.gradle.common.utils

object OsCheck {
    val isWindows: Boolean get() {
        return System.getProperties().getProperty("os.name")
            .lowercase().indexOf("WINDOWS") != -1;
    }
}