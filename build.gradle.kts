plugins {
    kotlin("jvm") version "1.9.21"
}

group = "dev.nyon"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.ajalt.mordant:mordant:2.2.0")
}

kotlin {
    jvmToolchain(8)
}