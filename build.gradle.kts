plugins {
    kotlin("jvm") version "2.1.0"
}

group = "dev.nyon"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.ajalt.mordant:mordant:3.0.1")
}

kotlin {
    jvmToolchain(8)
}