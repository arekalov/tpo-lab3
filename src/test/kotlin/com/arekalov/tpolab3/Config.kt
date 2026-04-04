package com.arekalov.tpolab3

import java.util.Properties
import java.io.FileInputStream

object Config {
    private val props = Properties().apply {
        val file = java.io.File("local.properties")
        if (file.exists()) {
            load(FileInputStream(file))
        }
    }

    val email: String get() = props.getProperty("so.email")
        ?: error("so.email not found in local.properties")

    val password: String get() = props.getProperty("so.password")
        ?: error("so.password not found in local.properties")
}
