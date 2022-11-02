plugins {
    kotlin("jvm") version "1.7.20"
    id("application")
}

group = "kirilov.vladislav"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.commons:commons-lang3:3.12.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("vlad.kirilov.MainKt")
}