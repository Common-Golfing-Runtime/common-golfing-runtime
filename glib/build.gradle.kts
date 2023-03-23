plugins {
    kotlin("multiplatform")
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
        binaries.library()
        browser()
        nodejs()
    }
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                api("ch.obermuhlner:big-math:2.3.2")
            }
        }
        val jvmTest by getting
        val jsMain by getting {
            dependencies {
                api(npm("decimal.js", "10.4.3", generateExternals = true))
            }
        }
        val jsTest by getting
    }
}