plugins {
    kotlin("multiplatform")
    application
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "io.github.cgr"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":glib"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                api("org.msgpack:msgpack-core:0.9.3")
                implementation("com.github.ajalt.clikt:clikt:3.5.0")
            }
        }
        val jvmTest by getting
        val jsMain by getting {
            dependencies {
                api(npm("@msgpack/msgpack", "3.0.0-beta1"))
            }
        }
        val jsTest by getting
    }
}

application {
    mainClass.set("io.github.cgr.app.MainKt")
}

tasks.shadowJar {
    archiveFileName.set("cgr.jar")
}