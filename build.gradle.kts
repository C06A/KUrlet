plugins {
    kotlin("multiplatform") version "1.9.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
    id("convention.publication")
}

group = "com.helpchoice.kotlin"
version = "2.1.2"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations
            .all {
                kotlinOptions.jvmTarget = "1.8"
            }
        withJava()
        testRuns["test"]
            .executionTask
            .configure {
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
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
    }

    macosX64()
    macosArm64()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    tvosArm64()
    tvosSimulatorArm64()
    tvosX64()
    
    mingwX64()
    linuxX64()
    linuxArm64()

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.4")
                implementation("org.jetbrains.kotlin:kotlin-serialization:1.9.10")
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
    }
}
