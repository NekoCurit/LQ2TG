val slf4jVersion: String by project
val apacheLog4jVersion: String by project
val sqliteJdbcVersion: String by project
val exposedVersion: String by project
val rOneBotVersion: String by project
val tgBotVersion: String by project
val kamlVersion: String by project

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")

    application
}

application {
    mainClass.set("net.nekocurit.lq2tg.LQ2TGLoader")
}

repositories {
    mavenCentral()
    maven("https://repo.maven.rtast.cn/releases/")
}

dependencies {
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.apache.logging.log4j:log4j-core:$apacheLog4jVersion")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:$apacheLog4jVersion")

    implementation("org.xerial:sqlite-jdbc:${sqliteJdbcVersion}")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

    implementation("cn.rtast.rob:ronebot-onebot-v11:$rOneBotVersion")
    implementation("dev.inmo:tgbotapi:$tgBotVersion")
    implementation("com.charleskorn.kaml:kaml:$kamlVersion")
}


tasks {
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }

    kotlin {
        jvmToolchain(17)
    }



    startScripts {
        doLast {
            windowsScript.apply {
                windowsScript
                    .readText()
                    .replace(
                        Regex("set CLASSPATH=.*"),
                        """set CLASSPATH=%APP_HOME%\\lib\\*"""
                    )
                    .also { writeText(it) }
            }
        }
    }
}