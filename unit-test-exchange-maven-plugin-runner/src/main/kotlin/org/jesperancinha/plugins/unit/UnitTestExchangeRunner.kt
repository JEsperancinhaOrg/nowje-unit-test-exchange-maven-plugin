package org.jesperancinha.plugins.unit

import java.io.File


/**
 * Input parameters
 *
 * Choice
 * Root directory
 *
 */
fun main(args: Array<String>) {
    println("Hello, World")
    val choice = args[0]
    val rootDir = args[1]

    File(rootDir).walkTopDown().forEach {
        println(it)
        if (it.isFile && it.extension.isSupported) {
            processFile(it)
        }
    }
}

private fun processFile(testFile: File) {
    val originalText = testFile.readLines().joinToString("\n")
    val convertedTest = originalText.processTests
    testFile.writeText(convertedTest)
}
