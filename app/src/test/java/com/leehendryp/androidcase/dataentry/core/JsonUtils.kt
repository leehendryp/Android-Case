package com.leehendryp.androidcase.dataentry.core

import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

fun getStringJson(fileName: String): String {
    val loader = ClassLoader.getSystemClassLoader()

    return Files.lines(Paths.get(loader.getResource(fileName).toURI()))
        .parallel()
        .collect(Collectors.joining())
}