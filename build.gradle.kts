plugins {
    kotlin("jvm") version "2.0.21"

    application
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

application {
    mainClass.set("dev.nyon.aoc.Day08Kt")
}