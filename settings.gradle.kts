rootProject.name = "LQ2TG"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    val kotlinVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion
    }
}