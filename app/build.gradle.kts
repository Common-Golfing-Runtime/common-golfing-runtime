plugins {
    kotlin("multiplatform") version "1.8.10"
    application
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
            kotlinOptions.jvmTarget = "1.8"
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
                implementation("org.msgpack:msgpack-core:0.9.3")
            }
        }
        val jvmTest by getting
        val jsMain by getting {
            dependencies {
                implementation(npm("@msgpack/msgpack", "3.0.0-beta1"))
            }
        }
        val jsTest by getting
    }
}

application {
    mainClass.set("io.github.cgr.app.MainKt")
}