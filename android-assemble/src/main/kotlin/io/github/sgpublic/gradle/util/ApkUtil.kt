package io.github.sgpublic.gradle.util

import io.github.sgpublic.gradle.AndroidAssemblePlugin
import io.github.sgpublic.gradle.core.BuildTypes
import net.dongliu.apk.parser.ApkFile
import org.gradle.internal.os.OperatingSystem
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

object ApkUtil {
    fun assembleApkAndLocate(name: String, outputFile: File, targetPath: String) {
        if (!outputFile.exists() || outputFile.extension != "apk") {
            return
        }
        val assemble = AndroidAssemblePlugin.rootProject.file(targetPath)

        val apkName = AndroidAssemblePlugin.rootProject.name + ApkFile(outputFile).use {
            it.apkMeta.let { meta ->
                meta.revisionCode
                if (name.contains(BuildTypes.TYPE_RELEASE)) {
                    return@let " V${meta.versionName}(${meta.versionCode})"
                } else if (name.contains(BuildTypes.TYPE_BETA)) {
                    return@let "_${meta.versionName}"
                } else if (name.contains(BuildTypes.TYPE_ALPHA)) {
                    return@let "_${meta.versionName}"
                } else {
                    return
                }
            }
        }

        val copy = copy(outputFile, File(assemble, "$apkName.${outputFile.extension}"))
        AndroidAssemblePlugin.logger.info("Output apk file: $copy")
        val currentOs = OperatingSystem.current()
        val runtime = Runtime.getRuntime()
        if (currentOs.isWindows) {
            runtime.exec("explorer.exe /select, $copy")
        }
    }

    private val CurrentDate get() = SimpleDateFormat("yyMMdd-HHmm").format(Date())
    fun assembleAarAndLocate(name: String, outputFile: File, targetPath: String) {
        if (!outputFile.exists() || outputFile.extension != "aar") {
            return
        }
        val assemble = AndroidAssemblePlugin.rootProject.file(targetPath)

        val copy = copy(outputFile, File(assemble, "$name-$CurrentDate.${outputFile.extension}"))
        AndroidAssemblePlugin.logger.info("Output apk file: $copy")
        val currentOs = OperatingSystem.current()
        val runtime = Runtime.getRuntime()
        if (currentOs.isWindows) {
            runtime.exec("explorer.exe /select, $copy")
        }
    }

    private fun copy(origin: File, target: File): File {
        if (!origin.exists()) {
            throw NoSuchFileException(origin)
        }
        if (target.exists() && !target.delete()) {
            throw FileAlreadyExistsException(origin, target, "Tried to overwrite the destination, but failed to delete it.")
        }
        if (origin.isDirectory) {
            throw IllegalStateException("Copy a directory not support!")
        }

        if (target.parentFile != null) {
            target.parentFile.mkdirs()
        }

        copy(origin.inputStream(), target.outputStream())
        return target
    }

    private val buffer = ByteArray(8 * 1024)
    private fun copy(origin: InputStream, target: OutputStream): Long {
        var bytesCopied = 0L
        var bytes = origin.read(buffer)
        while(bytes >= 0) {
            target.write(buffer, 0, bytes)
            bytesCopied += bytes
            bytes = origin.read(buffer)
        }
        origin.close()
        target.flush()
        target.close()
        return bytesCopied
    }
}